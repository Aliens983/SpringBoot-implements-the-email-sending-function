This is a template for a SpringBoot project sent via email. In \src\main\resources\application.yml, you can modify the configuration to send emails via 163 email or QQ email.

You can test the email sending function in postman.

You should correctly construct and submit the HTTP request body in JSON format in postman. Your format should be as follows：



```
 {
    "to":"The email address you want to send it to",
    "subject":"The subject of the email",
    "content":"The content of the email"
  }
```


