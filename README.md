# Backend Mail Service

This is an E-Mail Service implemented using Java Mail API.

## Java Mail API

A platform and protocol independent framework to build mail and messaging applications

Include the following dependency in the pom.xml file

```
<dependency>
    <groupId>com.sun.mail</groupId>
    <artifactId>javax.mail</artifactId>
    <version>1.6.2</version>
</dependency>
```

## Important Classes and Interfaces..

<hr>

### java.util.Properties:
The Properties class represents a persistent set of properties.
The Properties can be saved to a stream or loaded from a
stream.

<hr>

### .javax.mail. Message:
This class models an email message. To send a
message, subclass of Message (e.g. Mime Message) is
instantiated, the attributes and content are filled in, and
message is sent using the Transport.send method.

<hr>

### javax.mail.MessagingException:
This is base class for all exceptions thrown by the Messaging
classes

<hr>

### import javax.mail.PasswordAuthentication:
This class is simply a repository for a user name and a
password

<hr>

### javax.mail.Session:
Session class represents a mail session.

<hr>

### javax.mail.Transport:
This is abstract class that models a message transport.

<hr>

### javax.mail.internet.InternetAddress:
This class represents an Internet email address using the
syntax of RFC822

<hr>

### import javax.mail.internet.MimeMessage:
This class represents a MIME style email message. It
implements the Message abstract class and the MimePart
interface.

<hr>

## How to use

### Parameters

<ul>
<li>Pass the message as String</li>
<li>Pass the subject of mail as String</li>
<li>Pass the recipient mail addresses as String array</li>
<li>Pass the sender mail address as String</li>
<li>Pass the attachment links as String array</li>
</ul>

### Methods

<ul>
<li><strong>sendMail(..)</strong> : Use this method to send mail without any attachments</li>

<li><strong>sendMailWithAttachments(..)</strong> : Use this method to send mail with attachments</li>
</ul>

### mail.properties

Give the mail details as parameters in mail.properties file

### auth.properties

Give the email ans password in auth.properties file

