<%-- 
    Document   : index
    Created on : Apr 20, 2018, 8:28:45 AM
    Author     : zimma
--%>

<%@page import="java.net.URL"%>
<%@page import="java.io.InputStream"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.File"%>
<%@page import="java.util.Random"%>
<%@page import="java.security.SecureRandom"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <script src="https://apis.google.com/js/platform.js" async defer></script>
        <script src="http://code.jquery.com/jquery-1.8.3.js"></script>
        <meta name="google-signin-client_id" 
              content="732349035499-bgn20kn5k8or0h0uu468v5srrtavppm5.apps.googleusercontent.com">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Index</title>
        <link type="text/css" rel="stylesheet" href="styles/startpage-style.css">
    </head>
    <body id="startpage-body" onload="LoadPageBK()">
        <form method="POST" id="form-reg" action="organizer.jsp" accept-charset="UTF-8">
            <div class="name">DayPlan</div>
            <center><p id="about0"></p></center><br><br>
            <center>
                <div class="gbutton-st" onclick="handleClientLoad()" id="authorize-button">
                    <img src="img/AddingStyleElement/Google_Icon.jpg" class="gImg"></img>
                    <div id="gtextid" class="gtext">Sign in with Google</div>
                </div>
            </center>
            <br><br>
            <div class="info">
                <p id="about1">
                </p>
                <tag id="about2">
                </tag>
            </div>
            <input type="hidden" id="useremail" name="useremail">
            <input type="hidden" id="userid" name="userid">
            <input type="hidden" id="userimageurl" name="userimageurl">
            <input type="hidden" id="username" name="username">
            <input type="hidden" id="lang" name="lang" value="<%=request.getParameter("lang") == null ? "ru" : request.getParameter("lang")%>">
        </form>
        <script>
            /**
             * Load random BackGround images
             * @return {void}
             */
            function LoadPageBK() {
                var urlString = 'url(img/BackGround-Start/Background' +
            <%
                Random rand = new Random();
                out.print(Math.abs(rand.nextInt() % 2));
            %>
                + '.jpg)';
                document.getElementById('startpage-body').style.backgroundImage = urlString;
                LoadLangSettings();
            }
        </script>
        <div class="language-box">
            <img onclick="ChangeLanguage(event)" id="en" class="language" src="img/Languages/en.png">
            <img onclick="ChangeLanguage(event)" id="ru" class="language" src="img/Languages/ru.png">
        </div>
    </body>
</html>

<script type="text/javascript">
    // Client ID and API key from the Developer Console
    var CLIENT_ID = '732349035499-bgn20kn5k8or0h0uu468v5srrtavppm5.apps.googleusercontent.com';
    var API_KEY = 'AIzaSyAQsBoneCnVVInuburWbwO4--Fq8HscwP8';

    // Array of API discovery doc URLs for APIs used by the quickstart
    var DISCOVERY_DOCS = ["https://www.googleapis.com/discovery/v1/apis/calendar/v3/rest"];

    // Authorization scopes required by the API; multiple scopes can be
    // included, separated by spaces.
    var SCOPES = "https://www.googleapis.com/auth/calendar.readonly";

    var authorizeButton = document.getElementById('authorize-button');

    /**
     *  On load, called to load the auth2 library and API client library.
     */
    function handleClientLoad() {
        gapi.load('client:auth2', initClient);
    }

    /**
     *  Initializes the API client library and sets up sign-in state
     *  listeners.
     */

    function initClient() {
        gapi.client.init({
            apiKey: API_KEY,
            clientId: CLIENT_ID,
            discoveryDocs: DISCOVERY_DOCS,
            scope: SCOPES
        }).then(function () {
            // Listen for sign-in state changes.
            gapi.auth2.getAuthInstance().isSignedIn.listen(updateSigninStatus);
            // Handle the initial sign-in state.
            updateSigninStatus(gapi.auth2.getAuthInstance().isSignedIn.get());
            authorizeButton.onclick = handleAuthClick;
        });
    }

    /**
     * Change Language
     * @param {event} event
     * @return {void}
     */
    function ChangeLanguage(event) {
        if (event.target.id === '<%=request.getParameter("lang") == null ? "ru" : request.getParameter("lang")%>')
            return;
        switch (event.target.id) {
            case "ru":
                window.location = "?lang=ru";
                break;
            case "en":
                window.location = "?lang=en";
                break;
        }
    }

    /**
     * Load language
     * @return {void}
     */
    function LoadLangSettings() {
        var language = '<%=request.getParameter("lang") == null ? "ru" : request.getParameter("lang")%>';
        document.getElementById(language).src = 'img/Languages/' + language + '-active.png';
        switch (language) {
            case "ru":
                $.getJSON("sitelang/ru.json", function (data) {
                    SetTextLang(data);
                });
                break;
            case "en":
                $.getJSON("sitelang/en.json", function (data) {
                    SetTextLang(data);
                });
                break;
        }
    }

    /**
     * SetText Common
     * @param {JSON} data
     * @return {void}
     */
    function SetTextLang(data) {
        document.getElementById('about0').innerHTML = data["index"]["about0"];
        document.getElementById('about1').innerHTML = data["index"]["about1"];
        document.getElementById('about2').innerHTML = data["index"]["about2"];
    }

    /**
     *  Called when the signed in status changes, to update the UI
     *  appropriately. After a sign-in, the API is called.
     */
    function updateSigninStatus(isSignedIn) {
        if (isSignedIn) {
            handleAuthClick(isSignedIn);
        }
    }
    /**
     *  Sign in the user upon button click.
     */
    function handleAuthClick(event) {
        gapi.auth2.getAuthInstance().signIn().then(function (googleuser) {
            document.getElementById('gtextid').innerHTML = 'Signed in with Google';
            document.getElementById('useremail').value = googleuser.getBasicProfile().getEmail();
            document.getElementById('userid').value = googleuser.getBasicProfile().getId();
            document.getElementById('userimageurl').value = googleuser.getBasicProfile().getImageUrl();
            document.getElementById('username').value = googleuser.getBasicProfile().getGivenName() + ' ' + googleuser.getBasicProfile().getFamilyName();
            document.getElementById('form-reg').submit();
        });
    }

</script>

<script async defer src="https://apis.google.com/js/api.js"
        onreadystatechange="if (this.readyState === 'complete') this.onload()">
</script>