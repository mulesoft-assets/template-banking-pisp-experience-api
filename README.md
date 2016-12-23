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
Merchant must first register itself and receive client_id and client_secret. Payment information is encoded in JWT format
and signed with merchant's registered client_secret. Resulting JWT token is attached as request query parameter.
For further information about query parameters, please refer to the RAML file.

After user follows the redirect to the PISP initiate endpoint, he is redirected to the authorization server to validate
user identity (authorization server interaction is simulated inside this project). Upon successful authentication, self-contained access_token in JWT form (signed with RSA and encrypted with AES) is generated.
User is forwarded to the account selection/consent page with the access_token and when user confirms the payment, Payments process API is invoked to make payment and to notify the user via SMS about the transaction.
User should be redirected back to the merchant page defined in the payment initiation request token.

# Considerations <a name="considerations"/>

To make this Anypoint Template run, there are certain preconditions that must be considered. **Failling to do so could lead to unexpected behavior of the template.**

## APIs security considerations <a name="apissecurityconsiderations"/>
This API is meant to be deployed to CloudHub and managed using the API Platform Manager.

# Run it! <a name="runit"/>
Simple steps to get AISP Experience API running.
See below.

## Running on premise <a name="runonopremise"/>
In this section we detail the way you should run your Anypoint Template on your computer.


### Where to Download Anypoint Studio and Mule ESB
First thing to know if you are a newcomer to Mule is where to get the tools.

+ You can download Anypoint Studio from this [Location](https://www.mulesoft.com/platform/studio)
+ You can download Mule ESB from this [Location](https://www.mulesoft.com/platform/soa/mule-esb-open-source-esb)

### Importing an Anypoint Template into Studio
Anypoint Studio offers several ways to import a project into the workspace, for instance: 

+ Anypoint Studio generated Deployable Archive (.zip)
+ Anypoint Studio Project from External Location
+ Maven-based Mule Project from pom.xml
+ Mule ESB Configuration XML from External Location

You can find a detailed description on how to do so in this [Documentation Page](https://docs.mulesoft.com/anypoint-studio/v/6/importing-and-exporting-in-studio).

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
Anypoint Studio provides you with really easy way to deploy your Template directly to CloudHub, for the specific steps to do so please check this [link](https://docs.mulesoft.com/mule-user-guide/v/3.8/deploying#DeployingMuleApplications-DeploytoCloudHub)

### Applying policies on CloudHub <a name="applyingpolicies"/>
When a Mule application is deployed using the Mule 3.8.2+ Runtime, the API Manager allows you to dynamically apply different policies that can be used for securing the application, among many other things. More information can be found in [API Manager policies documentation](https://docs.mulesoft.com/api-manager/using-policies)

## Properties to be configured (With examples) <a name="propertiestobeconfigured"/>
In order to use this Mule Anypoint Template you need to configure properties (Credentials, configurations, etc.) either in properties file or in CloudHub as Environment Variables. 

Detailed list with examples:
### Application properties
+ http.port `8081`

### Merchant details
+ merchant.client_id `asdfjasksiwasdkjf`
+ merchant.client_secret `ns4fQc14Zg4hKFCNaSzArVuwszX95X14Ga12GY`
+ merchant.redirect_uri `https://demo-merchant.example.com/afterPayment`
+ merchant.url `https://merchant.example.com`

+ pisp.confirmation_url `http://localhost:8082/api/payment/account`
+ pisp.expected_audience `https://pisp-endpoint.cloudhub.io`


### Accounts Process API
+ api.banking.accounts.host `accounts-papi.example.com`
+ api.banking.accounts.port `80`
+ api.banking.accounts.basepath `/api`

### Payments Process API
+ api.banking.payments.host `payments-papi.example.com`
+ api.banking.payments.port `80`
+ api.banking.payments.basePath `/api`

### User Info
+ user.user_id `af990662-cd5b-0012-a21e-d829892e3be7`
+ user.user_ssn `124-123-22444`
+ user.username `john`
+ user.password `doe`

### Authorization server details
+ as.authorize_url `http://localhost:8082/api/oauth2/authorize`
+ as.private.key.path `jwk-pair.jwk`
+ as.signing.algorithm `RS512`
+ as.encryption.algorithm `A128GCM`
+ as.encryption.key.path `shared-key.jwk`
+ as.issuer `https://anypoint-bank.cloudhub.io`
+ as.jwks.url `https://example.com/public-keys.jwks`
