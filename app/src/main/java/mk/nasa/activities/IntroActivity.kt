package mk.nasa.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import mk.nasa.databinding.ActivityIntroBinding
import android.text.Html
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import mk.nasa.R
import mk.nasa.framework.call
import mk.nasa.framework.getBooleanPreference
import mk.nasa.framework.setBooleanPreference
import mk.nasa.framework.startActivity
import mk.nasa.pagetransformers.ZoomOutPageTransformer

class IntroActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityIntroBinding
    private lateinit var dots: Array<TextView?>
    private val introSlidesLayouts = intArrayOf(
        R.layout.intro_slide_1,
        R.layout.intro_slide_2
    )

    private var pageAdapter = object : PagerAdapter() {
        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val view: View = layoutInflater.inflate(
                introSlidesLayouts[position], container, false
            )
            container.addView(view)
            return view
        }

        override fun getCount(): Int {
            return introSlidesLayouts.size
        }

        override fun isViewFromObject(view: View, obj: Any): Boolean {
            return view === obj
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            val view = `object` as View
            container.removeView(view)
        }
    }

    private var pageChangeListener: ViewPager.OnPageChangeListener = object :
        ViewPager.OnPageChangeListener {
        var prevPos = 0
        override fun onPageSelected(position: Int) {
            updateSelectedDot(prevPos, position)
            val isLastPage = position == introSlidesLayouts.size - 1
            binding.btnNext.visibility = if (isLastPage) View.GONE else View.VISIBLE
            binding.btnSkip.visibility = if (isLastPage) View.GONE else View.VISIBLE
            binding.btnGotIt.visibility = if (isLastPage) View.VISIBLE else View.GONE
            prevPos = position
        }

        override fun onPageScrolled(arg0: Int, arg1: Float, arg2: Int) {}
        override fun onPageScrollStateChanged(arg0: Int) {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroBinding.inflate(layoutInflater)
        if (getBooleanPreference(getString(R.string.viewed_intro_slides))) {
            startHostActivity()
            finish()
        }

        setContentView(binding.root)

        initVariables()
        setUpListeners()
        addDots()
    }

    override fun onClick(v: View) {
        when (v) {
            binding.btnSkip, binding.btnGotIt -> startHostActivity()
            binding.btnNext -> showNextSlide()
        }
    }

    private fun initVariables() {
        binding.viewPager.adapter = pageAdapter
        binding.viewPager.setPageTransformer(true, ZoomOutPageTransformer())
    }

    private fun setUpListeners() {
        binding.btnGotIt.setOnClickListener(this)
        binding.btnSkip.setOnClickListener(this)
        binding.btnNext.setOnClickListener(this)
        binding.viewPager.addOnPageChangeListener(pageChangeListener)
    }

    private fun addDots() {
        binding.dotsContainer.removeAllViews()
        dots = arrayOfNulls(introSlidesLayouts.size)

        for (i in introSlidesLayouts.indices) {
            var dot = TextView(this).apply {
                this.text = Html.fromHtml("&#8226;")
                this.textSize = 35.toFloat()
                this.setTextColor(getColor(R.color.dot_inactive))
            }
            dots[i] = dot
            binding.dotsContainer.addView(dot)
        }

        updateSelectedDot(0, 0)
    }

    private fun updateSelectedDot(prevPosition: Int, currPosition: Int) {
        if (dots.size > prevPosition && dots.size > currPosition) {
            dots[prevPosition]?.setTextColor(resources.getColor(R.color.dot_inactive))
            dots[currPosition]?.setTextColor(resources.getColor(R.color.dot_active))
        }
    }

    private fun startHostActivity() {
        setBooleanPreference(getString(R.string.viewed_intro_slides), true)
        call() { startActivity<HostActivity>() }
    }

    private fun showNextSlide() {
        // Check for last page
        // If it is the last page host activity will be started
        val nextIndex = binding.viewPager.currentItem + 1
        if (nextIndex < introSlidesLayouts.size) {
            binding.viewPager.currentItem = nextIndex
        }
    }
}