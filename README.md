# ontario-tax-calculator-app
Requirements:
Design and code a tax calculation app for Ontario based on the tax calculation rules
found in “Combined Federal & Ontario Tax Brackets and Tax Rates Including Surtaxes”
section of https://www.taxtips.ca/taxrates/on.htm.
The app will also account for RRSP contributions (i.e deduct RRSP from income when
calculating tax, and display RRSP contribution limit for next year). RRSP limits are
found on https://www.canada.ca/en/revenue-agency/services/tax/registered-plansadministrators/
pspa/mp-rrsp-dpsp-tfsa-limits-ympe.html.
Your app should include a tax calculation class that takes care of everything related to
taxation and one activity that shows a proper UI to the user and call the tax calculation
methods properly. UI choices are up to your creative imagination. The app should allow
its users to do some what-if analysis by providing a Slider to choose a multiplier (range:
$0 to $27,230) for the RRSP deduction limit and calculate the tax as the user moves the
slider. Here is a tutorial about Sliders: https://medium.com/analytics-vidhya/slidersmaterial-
component-for-android-5be61bbe6726.
App data should be stored in Shared Preferences and retrieved whenever the app starts. A
refresh button will be added to reload the data stored in shared preferences.
