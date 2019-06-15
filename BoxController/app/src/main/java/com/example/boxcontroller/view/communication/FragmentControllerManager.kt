package com.example.boxcontroller.view.communication

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction


class FragmentControllerManager
/**
 * Private constructor that initiates the instance
 *
 * @param activity                 Activity that contain the manager
 * @param idFromFrameToPutFragment The id from layout where the layouts will be placed (EX: R.id.content_layout)
 * @param initialFragmentType      The initial fragment type
 * @param supportFragmentManager   The android FragmentManager
 */
private constructor(
    /**
     * Activity that contain the manager
     */
    private val mActivity: Activity,
    /**
     * The id from layout where the layouts will be placed
     */
    private val mIdFromFrameToPutFragment: Int,
    /**
     * Map of fragment classes
     */
    private val mFragmentTypesMap: Map<String, Class<*>>,

    initialFragmentType: String,
    /**
     * The android FragmentManager
     */
    private val mSupportFragmentManager: FragmentManager
) {
    /**
     * The current fragment type on focus
     */
    /**
     * Return the type of the fragment on focus
     *
     * @return Type of the fragment on focus
     */
    private var currentFragmentType: String?

    /**
     * The current fragment instance on focus
     */
    /**
     * Return the instance of the fragment on focus
     *
     * @return Instance of the fragment on focus
     */
    private var currentFragment: Fragment?

    init {
        currentFragmentType = initialFragmentType

        currentFragment = createFragmentByType(currentFragmentType!!)

        val fragmentTransaction = mSupportFragmentManager.beginTransaction()
        fragmentTransaction.add(mIdFromFrameToPutFragment, currentFragment!!, initialFragmentType)
        fragmentTransaction.commit()
    }

    /**
     * Keep the manager updated about current fragment
     */
    fun updateManagerOnBackStackChange() {
        val lastItemOnStack = mSupportFragmentManager.getBackStackEntryCount() - 1
        if (lastItemOnStack < 0) {
            mActivity.onBackPressed()
        } else {
            currentFragmentType = mSupportFragmentManager.getBackStackEntryAt(lastItemOnStack).getName()
            currentFragment = getFragmentFromBackStackByTag(currentFragmentType)
        }
    }

    /**
     * Search for fragment on back stack
     *
     * @param fragmentToSearch The fragment type to search
     * @return Returns the Fragment instance on back stack or return null if there is not
     * instance of this type on the back stack
     */
    private fun getFragmentFromBackStackByTag(fragmentToSearch: String?): Fragment? {
        return mSupportFragmentManager.findFragmentByTag(fragmentToSearch)
    }

    /**
     * Change the focused fragment by another
     *
     * @param newFragment    The next fragment to be focused
     * @param addToBackStack Add the previous fragment to back stack
     */
    fun changeFragment(newFragment: String, addToBackStack: Boolean) {
        if (newFragment === currentFragmentType) return
        var fragmentToAppear: Fragment? = getFragmentFromBackStackByTag(newFragment)
        if (fragmentToAppear == null) {
            fragmentToAppear = createFragmentByType(newFragment)
        }

        val fragmentTransaction = mSupportFragmentManager.beginTransaction()
        fragmentTransaction.replace(mIdFromFrameToPutFragment, fragmentToAppear!!, newFragment)
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(currentFragmentType)
        }
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        fragmentTransaction.commit()

        currentFragmentType = newFragment
        currentFragment = fragmentToAppear
    }

    /**
     * Create a Fragment instance based on type on parameter
     *
     * @param newFragment Type of fragment to be created
     * @return Return a fragment instance of parameter type, or null if the class from enum is not a
     * fragment instance of if the constructor does not exist or the constructor is private
     */
    private fun createFragmentByType(newFragment: String): Fragment? {
        try {
            val fragmentClass = mFragmentTypesMap[newFragment]
            val fragment = fragmentClass!!.newInstance()
            return Fragment::class.java.cast(fragment)
        } catch (e: InstantiationException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }

        return null
    }

    companion object {

        /**
         * Factory method that create a instance of EasyFragmentManager
         *
         * @param activity                 Activity that contain the manager
         * @param idFromFrameToPutFragment The id from layout where the layouts will be placed (EX: R.id.content_layout)
         * @param initialFragmentType      The initial fragment type
         * @param supportFragmentManager   The android FragmentManager
         * @return Instance of EasyFragmentManager
         * @throws NullPointerException Throw exception if any parameter is null
         */
        @Throws(NullPointerException::class)
        fun newInstance(
            activity: Activity?,
            idFromFrameToPutFragment: Int,
            fragmentTypesMap: Map<String, Class<*>>?,
            initialFragmentType: String?,
            supportFragmentManager: FragmentManager?
        ): FragmentControllerManager {
            if (activity == null)
                throw NullPointerException("Activity null on parameter")
            if (fragmentTypesMap == null || fragmentTypesMap.isEmpty())
                throw NullPointerException("Map null or empty on parameter")
            if (initialFragmentType == null)
                throw NullPointerException("Fragment type null on parameter")
            if (supportFragmentManager == null)
                throw NullPointerException("SupportFragmentManager null on parameter")

            return FragmentControllerManager(
                activity, idFromFrameToPutFragment, fragmentTypesMap, initialFragmentType, supportFragmentManager
            )
        }
    }
}