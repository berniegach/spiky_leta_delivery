package com.spikingacacia.spikyletadelivery;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TermsAndConditions extends Preference
{
    private Context context;
    public TermsAndConditions(Context context)
    {
        super(context);
        setLayoutResource(R.layout.terms_and_conditions);
        this.context=context;
    }

    public TermsAndConditions(Context context, AttributeSet attrs)
    {
        super(context,attrs);
        setLayoutResource(R.layout.terms_and_conditions);
        this.context=context;
    }
    public TermsAndConditions(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context,attrs,defStyleAttr);
        setLayoutResource(R.layout.terms_and_conditions);
        this.context=context;
    }
    @Override
    public void onBindViewHolder(PreferenceViewHolder view)
    {
        super.onBindViewHolder(view);
        TextView textView = (TextView) view.findViewById(R.id.text);
        textView.setText(makeSpannable(setText(), "<b>(.+?)</b>", "<b>", "</b>"));

    }
    String setText()
    {
        String text = "<b>TERMS AND CONDITIONS</b> \n" +
                "<b>Introduction</b>\n" +
                "Welcome to Order. These are general terms of use and legal information (hereinafter, the “General Terms”) apply to the ORDER website (hereinafter, “Order”), whose domain is order.spikingacacia.com, and to its related mobile application, as well as to all its related sites or sites linked from order.spikingacacia.com  by order, as well as its affiliated companies and associates. By using ORDER, you agree to these terms of use. If you do not agree, please refrain from using it.\n" +
                "Order hereby makes available to users (hereinafter, the “User” or “Users”) the order website and mobile application (hereinafter, jointly, the “Platform”).\n" +
                "In accordance with the provisions of the applicable legislation, the following identification details of the site owner are provided below:\n" +
                "•\tCompany name: <b>Spiking Acacia</b> \n" +
                "•\tEmail: info@order.spikingacacia.com\n" +
                "\n" +
                "<b>Object</b>\n" +
                "ORDER is a technology platform, through which customers in a restaurant can scan a QR code to order (more content) by means of a mobile or web application on the platform (hereinafter, the “APP”) and, in an ancillary manner, where applicable and if so requested through the APP by users of the APP and consumers of the said restaurant, act as an intermediary in the immediate delivery of the order.\n" +
                "\n" +
                "<b>Terms of Use</b>\n" +
                "By accessing the Platform and voluntarily creating a profile, all Users acknowledge and expressly and unambiguously agree to these General Terms of Use and Contracting and to the Privacy Policy.\n" +
                " \n" +
                "<b>Access and Registration for Customers</b>\n" +
                "In order to be a Customer of the Platform, it is essential that you meet the following requirements:\n" +
                "•\tYou must be at least 18 years of age.\n" +
                "•\tYou must truthfully complete the mandatory fields of the registration form requesting personal details such as the user’s name, e-mail address, telephone number and bank card number.\n" +
                "•\tYou must agree to these Terms of Use and Contracting.\n" +
                "•\tYou must accept the Privacy and Data Protection Policy\n" +
                "The User warrants that all the information regarding his/her identity and capacity provided to ORDER in the registration forms for the Platform is true, accurate and complete. In addition, the User undertakes to keep his/her details up to date.\n" +
                "If a User provides any false, inaccurate or incomplete information or if ORDER considers that there are well founded reasons to doubt the truthfulness, accuracy or integrity of such information, ORDER may deny that User current or future access to, and use of, the Platform or any of its contents and/or services.\n" +
                "When registering on the Platform, Users must choose a username and password. Both the username and the password are strictly confidential, personal and non-transferable. In order to increase the security of accounts, ORDER recommends that users do not use the same login credentials as on other platforms. If a User uses the same login credentials as on other platforms, ORDER will be unable to guarantee the security of the account or ensure that the User is the only person logging into his/her profile.\n" +
                "Users undertake not to disclose the details of their account or allow access thereto to third parties. Users shall be solely responsible for any use of such details or of the services of the Site that may be made by third parties, including the statements and/or contents entered on the Platform, or for any other action carried out under their username and/or password.\n" +
                "The user will be the sole owner of the content entered by him/her on the Platform. Furthermore, by registering on the Platform and agreeing to these Terms and Conditions, the User grants ORDER, in relation to the content that he/she may provide, a worldwide, irrevocable, and transferable license, free of charge, with the right to sub-license, use, copy, modify, create derivative works, distribute, publicize and exploit it in any way that may be deemed appropriate by ORDER, with or without any further communication to the User and without having to pay any amounts for such uses.\n" +
                "ORDER cannot guarantee the identity of registered Users, and it will therefore not be liable for the use by unregistered third parties of a registered User’s identity. Users undertake to inform ORDER immediately, using the communication channels made available by ORDER, if their credentials are stolen, disclosed or lost.\n" +
                "\n<b>Profile</b>\n" +
                "In order to complete their registration on the Platform, Users must provide certain details, such as username, e-mail address, telephone number, bank card details, etc. Once they have completed the registration process, all Users can access their profile and complete or edit it, and/or deregister, as they deem appropriate. ORDER does not store users’ payment data, which shall be processed and stored by the payment service provider as described in these Terms and Conditions and in the Privacy Policy.\n" +
                "<b>\nCredit Card Theft or Removal</b>\n" +
                "Since ORDER cannot guarantee the identity of registered Users, Users are under an obligation to inform ORDER if they have evidence that the credit card associated with their ORDER profile has been stolen and/or is being fraudulently used by a third party. Therefore, since ORDER and its payment platform are proactive in the protection of Users through the use of appropriate security measures, if a User fails to inform ORDER of the missing card, ORDER will not be liable for any fraudulent use thereof that may be made by third parties on the User’s account. ORDER suggests that Users report any theft, robbery or suspected misuse of their credit card to the police.\n" +
                "If necessary, ORDER undertakes to cooperate with the User and the competent authorities to provide reliable evidence of the wrongly applied charge. In the event of fraud, ORDER reserves the right to take any action that may be necessary if it has been detrimentally affected by the misuse of the site. \n" +
                " \n" +
                "\n" +
                "<b>Prices of the Services and Billing</b>\n" +
                "Registration and use of the Platform is completely free for Customers.\n" +
                "\n" +
                "The Customer only has to pay for each order requested through the Platform. By registering through the Platform and providing the required bank details, Users expressly authorize ORDER to issue receipts for payment of the order.\n" +
                " <b>In accordance with these terms, the Customer will be entitled to know the price the food or beverage on ORDER before payment.</b> \n" +
                "<b>ORDER reserves the right to change its prices at any time. Such changes shall take effect immediately after publication. The User expressly authorizes ORDER to send him/her by electronic means, to the e-mail address provided by the User during the registration process, receipts for the bills generated.</b> \n" +
                "\n" +
                "<b>Price of food and beverage Appearing on the Platform</b>\n" +
                "All the prices stated on the Platform are inclusive of any taxes that may be applicable based on the territory from which the User operates and shall in any event be denominated in the currency in force in the territory from which the User operates.\n" +
                "However, the prices of the food and beverage sold in restaurants shown on the ORDER Platform may be indicative only. In any event, they don’t correspond to the food and beverage sold in restaurants, and the price is solely set by the restaurant. The Customer may contact the waiter to confirm the final price of the order.\n" +
                "\n" +
                "<b>Promotional Codes and/or Other Offers or Discounts</b>\n" +
                "Promotional codes and/or other offers or discounts offered on the Platform must be correctly entered in the application before placing the order. Otherwise, they will not take effect and the User will be unable to enjoy their benefits.  \n" +
                "ORDER may at any time and unilaterally provide credits to use within the Platform to certain users. The User acknowledges and accepts that the credits must be used within six (6) months from the date the credit is made available to the User on the Platform.\n" +
                "ORDER will accept no liability if, due to an event of force majeure or other events beyond its control or whose need is justified, it is forced to cancel, shorten, extend or amend the conditions of promotions.\n" +
                "In particular, ORDER will accept no liability if the website is not available at any time during promotions or for a malfunction in the automated promotion system.\n" +
                "\n" +
                " \n" +
                "<b>Price and Payment Method</b>\n" +
                "The price of the food and beverage shall be as stated by the restaurant on the Platform. However, the waiter should know that the price of some of the food and beverage may vary in real time due to availability at the establishments appearing on the platform, and that the Customer will in any event always be informed by the waiter before he/she accepts the order/payment.\n" +
                "The Customer may pay for the food or beverage via m-pesa or by credit card. The cash payment option may be unavailable in some of the countries in which ORDER operates. When making the order, the Customer will be informed of the various payment options available in the territory from which the service is being requested. In order to pay with a credit card, the Customer must provide the card details through the platform as a payment method associated with his/her account. ORDER does not store card numbers on its servers and can only view the last four digits of the card. The full information shall be stored on the servers of the payment service provider that makes the payments on ORDER’s behalf. Customers paying by credit card will incur no additional charges for choosing this payment method.\n" +
                "\n" +
                "<b> Fee for the Services</b>\n" +
                "It’s totally free\n" +
                "\n" +
                "<b>Purchases of Alcohol</b>\n" +
                "Users who place an order that includes the purchase and/or delivery of alcoholic drinks through the platform must be of legal age; i.e. 18 or older. When making an order that includes alcoholic drinks, the User confirms that he/she is at least 18 years old. ORDER reserves the right to refuse the order for the purchase and/or delivery of alcohol to any person who is unable to prove that they are at least 18 years old.\n" +
                "Similarly, in those cases and cities in which the sale and/or delivery of alcoholic drinks is restricted during a particular time slot, it is the User’s responsibility to place orders only during the times allowed under the applicable legislation. ORDER reserves the right to refuse the order for the purchase of alcohol outside the permitted times.\n";
        return text;
    }
    private SpannableStringBuilder makeSpannable(String text, String regex, String startTag, String endTag) {

        StringBuffer sb = new StringBuffer();
        SpannableStringBuilder spannable = new SpannableStringBuilder();

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            sb.setLength(0);
            String group = matcher.group();
            String spanText = group.substring(startTag.length(), group.length() - endTag.length());
            matcher.appendReplacement(sb, spanText);

            spannable.append(sb.toString());
            int start = spannable.length() - spanText.length();

            spannable.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), start, spannable.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        sb.setLength(0);
        matcher.appendTail(sb);
        spannable.append(sb.toString());
        return spannable;
    }
}
