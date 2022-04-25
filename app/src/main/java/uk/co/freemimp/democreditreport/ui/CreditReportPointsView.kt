package uk.co.freemimp.democreditreport.ui

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.view.animation.Interpolator
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import uk.co.freemimp.democreditreport.R
import uk.co.freemimp.democreditreport.extension.kotlin.reduceByEpsilon
import uk.co.freemimp.democreditreport.extension.kotlin.toRadians
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sign
import kotlin.math.sin

class CreditReportPointsView@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    // Arcs are drawn from 90° position, this is offsetting it back 90°
    private val startAngle = 2 * Math.PI * -1 / 4

    private lateinit var backgroundPaint: Paint
    private lateinit var innerArcPaint: Paint
    private lateinit var outerArcPaint: Paint
    private lateinit var arcEndPaint: Paint
    private lateinit var animator: ValueAnimator

    private val innerArcRectF = RectF()
    private val outerArcRectF = RectF()


    private var innerArcLineWidth: Float = 0f
    private var outerArcLineWidth: Float = 0f

    @ColorRes
    private var circleBackgroundColor: Int = 0

    @ColorRes
    private var innerArcProgressColor: Int = 0
    @ColorRes
    private var outerArcProgressColor: Int = 0

    private var backgroundRadius: Float = 0f
    private var arcRadius: Float = 0f
    private var arcAnimationDuration = 0L

    private var currentAngle: Float = 0f

    private var interpolator: Interpolator? = null

    init {
        isSaveEnabled = true

        if (attrs != null) {
            val attributesArray =
                context.obtainStyledAttributes(attrs, R.styleable.CreditReportPointsView)

            innerArcLineWidth = attributesArray.getDimensionPixelSize(
                R.styleable.CreditReportPointsView_innerArcLineWidth,
                resources.getDimensionPixelSize(R.dimen.inner_arc_line_width)
            ).toFloat()

            outerArcLineWidth = attributesArray.getDimensionPixelSize(
                R.styleable.CreditReportPointsView_outerArcLineWidth,
                resources.getDimensionPixelSize(R.dimen.outer_arc_line_width)
            ).toFloat()

            circleBackgroundColor = attributesArray.getResourceId(
                R.styleable.CreditReportPointsView_circleBackgroundColor,
                android.R.color.white
            )

            innerArcProgressColor = attributesArray.getResourceId(
                R.styleable.CreditReportPointsView_innerArcColor,
                android.R.color.black
            )

            outerArcProgressColor = attributesArray.getResourceId(
                R.styleable.CreditReportPointsView_outerArcColor,
                android.R.color.black
            )

            attributesArray.recycle()
        } else {
            innerArcLineWidth =
                resources.getDimensionPixelSize(R.dimen.inner_arc_line_width).toFloat()
            outerArcLineWidth =
                resources.getDimensionPixelSize(R.dimen.outer_arc_line_width).toFloat()
        }

        arcAnimationDuration =
            resources.getInteger(R.integer.progress_arc_animation_duration_millis).toLong()

        setCircleBackgroundColor(circleBackgroundColor)
        setInnerArcBackgroundColor(innerArcProgressColor)
        setOuterArcBackgroundColor(outerArcProgressColor)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

        backgroundRadius = min(width, height) / 2f
        arcRadius =
            backgroundRadius - (innerArcLineWidth / 2f) - resources.getDimensionPixelSize(R.dimen.progress_arc_line_padding)
        innerArcRectF.set(
            width / 2f - arcRadius,
            height / 2f - arcRadius,
            width / 2f + arcRadius,
            height / 2f + arcRadius
        )
        outerArcRectF.set(
            width / 2f + backgroundRadius,
            height / 2f + backgroundRadius,
            width / 2f - backgroundRadius,
            height / 2f - backgroundRadius
        )
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Draw background
        canvas.drawCircle(
            width / 2f,
            height / 2f,
            backgroundRadius,
            backgroundPaint
        )

        // Draw outer arc
        canvas.drawArc(
            outerArcRectF,
            0f,
            360f,
            false,
            outerArcPaint
        )

        // Draw inner arc
        canvas.drawArc(
            innerArcRectF,
            Math.toDegrees(startAngle).rem(DEGREES_IN_CIRCLE).toFloat(),
            Math.toDegrees(currentAngle.toDouble()).rem(DEGREES_IN_CIRCLE).toFloat(),
            false,
            innerArcPaint
        )

        // Draw progress arc end (for rounded end)]
        val angleEndAngle = startAngle + currentAngle
        canvas.drawCircle(
            (width / 2f) + (arcRadius * cos(angleEndAngle)).toFloat(),
            (height / 2f) + (arcRadius * sin(angleEndAngle)).toFloat(),
            innerArcLineWidth / 2f,
            arcEndPaint
        )
    }

    private fun setCircleBackgroundColor(@ColorRes colorRes: Int) {
        backgroundPaint = Paint().apply {
            color = ContextCompat.getColor(context, colorRes)
            isAntiAlias = true
        }
    }

    private fun setInnerArcBackgroundColor(@ColorRes colorRes: Int) {
        innerArcPaint = Paint().apply {
            color = ContextCompat.getColor(context, colorRes)
            isAntiAlias = true
            style = Paint.Style.STROKE
            strokeWidth = innerArcLineWidth
        }

        arcEndPaint = Paint().apply {
            color = ContextCompat.getColor(context, colorRes)
            isAntiAlias = true
            style = Paint.Style.FILL
            strokeWidth = innerArcLineWidth / 2f
        }
    }

    private fun setOuterArcBackgroundColor(@ColorRes colorRes: Int) {
        outerArcPaint = Paint().apply {
            color = ContextCompat.getColor(context, colorRes)
            isAntiAlias = true
            style = Paint.Style.STROKE
            strokeWidth = outerArcLineWidth
        }
    }


    fun setInterpolator(interpolator: Interpolator) {
        this.interpolator = interpolator
    }

    fun setProgress(fromDegrees: Float, toDegrees: Float) {
        if (this::animator.isInitialized && animator.isRunning) {
            animator.cancel()
        }

        animator = ValueAnimator.ofFloat(
            fromDegrees.parseDegrees(),
            toDegrees.parseDegrees()
        )
            .apply {
                duration = arcAnimationDuration
                interpolator = this@CreditReportPointsView.interpolator
                addUpdateListener {
                    currentAngle = it.animatedValue as Float
                    invalidate()
                }

                start()
            }
    }

    /**
     * Parses degrees to radians
     */
    private fun Float.parseDegrees(): Float {
        return if ((this > 0 || this < 0) && rem(DEGREES_IN_CIRCLE).sign == 0f) {
                DEGREES_IN_CIRCLE
            } else {
                rem(DEGREES_IN_CIRCLE)
            }.toFloat().toRadians().reduceByEpsilon()
    }

    private companion object {
        private const val DEGREES_IN_CIRCLE = 360f
    }
}