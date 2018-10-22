# Template Banking Payments Initiation Service Provider Experience API

+ [License Agreement](#licenseagreement)
+ [Use Case](#usecase)
+ [Considerations](#considerations)
	* [APIs security considerations](#apissecurityconsiderations)
+ [Run it!](#runit)
	* [Running on premise](#runonopremise)
	* [Running on Studio](#runonstudio)
	* [Running on Mule ESB stand alone](#runonmuleesbstandalone)
	* [Running on CloudHub](#runoncloudhub)
	* [Deploying your Anypoint Template on CloudHub](#deployingyouranypointtemplateoncloudhub)
	* [Applying policies on CloudHub](#applyingpolicies)
	* [Properties to be configured (With examples)](#propertiestobeconfigured)

# License Agreement <a name="licenseagreement"/>
Note that using this template is subject to the conditions of this [License Agreement](AnypointTemplateLicense.pdf).
Please review the terms of the license before downloading and using this template. In short, you are allowed to use the template for free with Mule ESB Enterprise Edition, CloudHub, or as a trial in Anypoint Studio.

# Use Case <a name="usecase"/>
This API allows to initiate payment via bank directly by pre-registered merchant.
Merchant must first register itself and receive client_id. Payment information is encoded in JWS format
and signed with merchant's generated One Time Signing Key. Resulting JWS token is attached as request query parameter and sent to /initiate PISP endpoint.
For further information about query parameters, please refer to the RAML file.

After user follows the redirect to the PISP initiate endpoint, he/she is redirected to the authorization server to validate
user identity. Upon successful authentication, self-contained access_token in JWT form (signed with RSA and encrypted with AES) is generated.
User is then redirected back to the PISP consent page to select account from which the payment should be executed. Once the user confirms the payment, two factor authentication process begins and 4 digit code is sent to user's mobile phone registered with the bank account. Page to enter the code is shown and when user hits 'Pay now' button, code is verified and if everything is OK, Payment process API is invoked to execute the payment and notify the user via SMS about the transaction.
After successful payment, user is redirected back to the merchant page defined in the payment initiation request token.

# Considerations <a name="considerations"/>

To run this Anypoint Template there are certain preconditions that must be considered. **Failing to do so could lead to unexpected behavior of the template.**

## APIs security considerations <a name="apissecurityconsiderations"/>
This API is meant to be deployed to CloudHub and managed using the API Platform Manager.

# Run it! <a name="runit"/>
Simple steps to get PISP Experience API running.
See below.

## Running on premise <a name="runonopremise"/>
In this section we detail the way you should run your Anypoint Template on your computer.


### Where to Download Anypoint Studio and Mule ESB
First thing to know if you are a newcomer to Mule is where to get the tools.

+ You can download Anypoint Studio from this [Location](https://www.mulesoft.com/platform/studio)
+ You can download Mule ESB from this [Location](https://www.mulesoft.com/platform/soa/mule-esb-open-source-esb)

### Importing an Anypoint Template into Studio
Anypoint Studio offers several ways to import a project into the workspace, for instance: 

+ Anypoint Studio Project from File System
+ Packaged mule application (.jar)

You can find a detailed description on how to do so in this [Documentation Page](http://www.mulesoft.org/documentation/display/current/Importing+and+Exporting+in+Studio).

### Running on Studio <a name="runonstudio"/>
Once you have imported you Anypoint Template into Anypoint Studio you need to follow these steps to run it:

+ Locate the properties file `mule-<env>.properties`, in src/main/app/resources
+ Complete all the properties required as per the examples in the section [Properties to be configured](#propertiestobeconfigured)
+ Once that is done, right click on you Anypoint Template project folder 
+ Hover you mouse over `"Run as"`
+ Click on  `"Mule Application"`

### Running on Mule ESB stand alone <a name="runonmuleesbstandalone"/>
Complete all properties in one of the property files, for example in [mule.prod.properties](../master/src/main/resources/mule.prod.properties) and run your app with the corresponding environment variable to use it. To follow the example, this will be `mule.env=prod`. 

## Running on CloudHub <a name="runoncloudhub"/>
While [creating your application on CloudHub](https://docs.mulesoft.com/runtime-manager/hello-world-on-cloudhub) (Or you can do it later as a next step), you need to go to `"Manage Application"` > `"Properties"` to set all environment variables detailed in **Properties to be configured**.
Follow other steps defined [here](#runonpremise) and once your app is all set and started, there is no need to do anything else.

### Deploying your Anypoint Template on CloudHub <a name="deployingyouranypointtemplateoncloudhub"/>
Anypoint Studio provides you with really easy way to deploy your Template directly to CloudHub, for the specific steps to do so please check this [link](http://www.mulesoft.org/documentation/display/current/Deploying+Mule+Applications#DeployingMuleApplications-DeploytoCloudHub)

### Applying policies on CloudHub <a name="applyingpolicies"/>
When a Mule application is deployed using the Mule 3.8.2+ Runtime, the API Manager allows you to dynamically apply different policies that can be used for securing the application, among many other things. More information can be found in [API Manager policies documentation](https://docs.mulesoft.com/api-manager/using-policies)

This API requires application of [Custom Client Credentials Enforcement policy](https://github.com/mulesoft/template-banking-pisp-policy) in order to validate the merchant when requesting the initial signing key (/key endpoint) to produce payment JWT token.
Merchant application must be registered via the Anypoint Platform Developer Portal to access this API.

## Properties to be configured (With examples) <a name="propertiestobeconfigured"/>
In order to use this Mule Anypoint Template you need to configure properties (Credentials, configurations, etc.) either in properties file or in CloudHub as Environment Variables. 

Detailed list with examples:
### Application properties
+ https.port `8082`


#HTTPS settings
keystore.location `keystore.jks`
keystore.password `keystore_password`
key.password `key_password`

##PISP API configuration (protocol://host:port/basePath)
pisp.baseUrl `https://pisp-exp-api.example.com:443/api`
pisp.client_id `client_id`
pisp.expected_audience `https://pisp-exp-api.example.com`
pisp.redirect_uri `https://${pisp.baseUrl}/consent`
pisp.confirmation_url `${pisp.baseUrl}/confirmPayment`
pisp.otp_url `${pisp.baseUrl}/code`

#Notifications System API
api.banking.notification.host `api.notification.example.com`
api.banking.notification.port `80`
api.banking.notification.basePath `/api`
api.banking.notification.protocol `HTTP`

#Accounts Process API
api.banking.accounts.host `api.accounts.example.com`
api.banking.accounts.port `80`
api.banking.accounts.basePath `/api`
api.banking.accounts.protocol `HTTP`

#Payments Process API
api.banking.payment.host `api.payment.example.com`
api.banking.payment.port `80`
api.banking.payment.basePath `/api`
api.banking.payment.protocol `HTTP`

#Authorization server details 
api.banking.auth.host `api.auth.example.com`
api.banking.auth.port `80`
api.banking.auth.basePath `/api`
api.banking.auth.protocol `HTTP`

#RSA key pair in JWK format
jwt.signing.key.path `jwk-pair.jwk`

#AES key in JWK format
jwt.resource.path `path/resourse`
jwt.encryption.key.path `shared-key.jwk`
jwt.issuer `https://auth.example.com`
jwt.audience `https://auth.exampleaudience.com`

