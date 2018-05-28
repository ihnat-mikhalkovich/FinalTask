function pagination(c, m) {
    var current = c,
        last = m,
        delta = 2,
        left = current - delta,
        right = current + delta + 1,
        range = [],
        rangeWithDots = [],
        l;

    for (let i = 1; i <= last; i++) {
        if (i == 1 || i == last || i >= left && i < right) {
            range.push(i);
        }
    }

    for (let i of range) {
        if (l) {
            if (i - l === 2) {
                rangeWithDots.push(l + 1);
            } else if (i - l !== 1) {
                rangeWithDots.push('...');
            }
        }
        rangeWithDots.push(i);
        l = i;
    }

    return rangeWithDots;
}

function executePagination(currentPage, pageAmount, orderBy, direction) {
    for (let i of pagination(currentPage, pageAmount)) {
        var liItem = "<li";
        if (i === currentPage) {
            liItem = liItem + " class='active'";
        }
        if (i === "...") {
            liItem = liItem + " class='disabled'><a href='/FrontController?commandType=users&page=" + currentPage +"&orderBy="+ orderBy +"&direction="+ direction +"'>" + i + "</a></li>";
        } else {
            liItem = liItem + "><a href='/FrontController?commandType=users&page=" + i +"&orderBy="+ orderBy +"&direction="+ direction +"'>" + i + "</a></li>";
        }
        $("#pagination").append(liItem);
    }
};

function executeOrdersPagination(currentPage, pageAmount) {
    for (let i of pagination(currentPage, pageAmount)) {
        var liItem = "<li";
        if (i === currentPage) {
            liItem = liItem + " class='active'";
        }
        if (i === "...") {
            liItem = liItem + " class='disabled'><a href='/FrontController?commandType=orders&page=" + currentPage +"'>" + i + "</a></li>";
        } else {
            liItem = liItem + "><a href='/FrontController?commandType=orders&page=" + i +"'>" + i + "</a></li>";
        }
        $("#pagination").append(liItem);
    }
}

function executeHistoryPagination(currentPage, pageAmount) {
    for (let i of pagination(currentPage, pageAmount)) {
        var liItem = "<li";
        if (i === currentPage) {
            liItem = liItem + " class='active'";
        }
        if (i === "...") {
            liItem = liItem + " class='disabled'><a href='/FrontController?commandType=history&page=" + currentPage +"'>" + i + "</a></li>";
        } else {
            liItem = liItem + "><a href='/FrontController?commandType=history&page=" + i +"'>" + i + "</a></li>";
        }
        $("#pagination").append(liItem);
    }
}

function executeUserHistoryPagination(currentPage, pageAmount, userId) {
    for (let i of pagination(currentPage, pageAmount)) {
        var liItem = "<li";
        if (i === currentPage) {
            liItem = liItem + " class='active'";
        }
        if (i === "...") {
            liItem = liItem + " class='disabled'><a href='/FrontController?commandType=userHistory&userId="+ userId +"&page=" + currentPage +"'>" + i + "</a></li>";
        } else {
            liItem = liItem + "><a href='/FrontController?commandType=userHistory&user_id="+ userId +"&page=" + i +"'>" + i + "</a></li>";
        }
        $("#pagination").append(liItem);
    }
}

$(document).ready(function(){
    var arrival_date_input=$('input[name="arrivalDate"]');
    var departure_date_input=$('input[name="departureDate"]');
    var container=$('.bootstrap-iso form').length>0 ? $('.bootstrap-iso form').parent() : "body";
    arrival_date_input.datepicker({
        format: 'yyyy-mm-dd',
        container: container,
        todayHighlight: true,
        autoclose: true
    });
    departure_date_input.datepicker({
        format: 'yyyy-mm-dd',
        container: container,
        todayHighlight: true,
        autoclose: true
    });
});

$(function() {
    $('input[name="dateRange"]').daterangepicker({
        minDate: moment().add(1, 'day'),
        startDate: moment().add(1, 'day'),
        endDate: moment().add(2, 'day'),
        locale: {
            format: 'YYYY-MM-DD'
        }
    });
});

var MD5 = function (string) {

    function RotateLeft(lValue, iShiftBits) {
        return (lValue<<iShiftBits) | (lValue>>>(32-iShiftBits));
    }

    function AddUnsigned(lX,lY) {
        var lX4,lY4,lX8,lY8,lResult;
        lX8 = (lX & 0x80000000);
        lY8 = (lY & 0x80000000);
        lX4 = (lX & 0x40000000);
        lY4 = (lY & 0x40000000);
        lResult = (lX & 0x3FFFFFFF)+(lY & 0x3FFFFFFF);
        if (lX4 & lY4) {
            return (lResult ^ 0x80000000 ^ lX8 ^ lY8);
        }
        if (lX4 | lY4) {
            if (lResult & 0x40000000) {
                return (lResult ^ 0xC0000000 ^ lX8 ^ lY8);
            } else {
                return (lResult ^ 0x40000000 ^ lX8 ^ lY8);
            }
        } else {
            return (lResult ^ lX8 ^ lY8);
        }
    }

    function F(x,y,z) { return (x & y) | ((~x) & z); }
    function G(x,y,z) { return (x & z) | (y & (~z)); }
    function H(x,y,z) { return (x ^ y ^ z); }
    function I(x,y,z) { return (y ^ (x | (~z))); }

    function FF(a,b,c,d,x,s,ac) {
        a = AddUnsigned(a, AddUnsigned(AddUnsigned(F(b, c, d), x), ac));
        return AddUnsigned(RotateLeft(a, s), b);
    };

    function GG(a,b,c,d,x,s,ac) {
        a = AddUnsigned(a, AddUnsigned(AddUnsigned(G(b, c, d), x), ac));
        return AddUnsigned(RotateLeft(a, s), b);
    };

    function HH(a,b,c,d,x,s,ac) {
        a = AddUnsigned(a, AddUnsigned(AddUnsigned(H(b, c, d), x), ac));
        return AddUnsigned(RotateLeft(a, s), b);
    };

    function II(a,b,c,d,x,s,ac) {
        a = AddUnsigned(a, AddUnsigned(AddUnsigned(I(b, c, d), x), ac));
        return AddUnsigned(RotateLeft(a, s), b);
    };

    function ConvertToWordArray(string) {
        var lWordCount;
        var lMessageLength = string.length;
        var lNumberOfWords_temp1=lMessageLength + 8;
        var lNumberOfWords_temp2=(lNumberOfWords_temp1-(lNumberOfWords_temp1 % 64))/64;
        var lNumberOfWords = (lNumberOfWords_temp2+1)*16;
        var lWordArray=Array(lNumberOfWords-1);
        var lBytePosition = 0;
        var lByteCount = 0;
        while ( lByteCount < lMessageLength ) {
            lWordCount = (lByteCount-(lByteCount % 4))/4;
            lBytePosition = (lByteCount % 4)*8;
            lWordArray[lWordCount] = (lWordArray[lWordCount] | (string.charCodeAt(lByteCount)<<lBytePosition));
            lByteCount++;
        }
        lWordCount = (lByteCount-(lByteCount % 4))/4;
        lBytePosition = (lByteCount % 4)*8;
        lWordArray[lWordCount] = lWordArray[lWordCount] | (0x80<<lBytePosition);
        lWordArray[lNumberOfWords-2] = lMessageLength<<3;
        lWordArray[lNumberOfWords-1] = lMessageLength>>>29;
        return lWordArray;
    };

    function WordToHex(lValue) {
        var WordToHexValue="",WordToHexValue_temp="",lByte,lCount;
        for (lCount = 0;lCount<=3;lCount++) {
            lByte = (lValue>>>(lCount*8)) & 255;
            WordToHexValue_temp = "0" + lByte.toString(16);
            WordToHexValue = WordToHexValue + WordToHexValue_temp.substr(WordToHexValue_temp.length-2,2);
        }
        return WordToHexValue;
    };

    function Utf8Encode(string) {
        string = string.replace(/\r\n/g,"\n");
        var utftext = "";

        for (var n = 0; n < string.length; n++) {

            var c = string.charCodeAt(n);

            if (c < 128) {
                utftext += String.fromCharCode(c);
            }
            else if((c > 127) && (c < 2048)) {
                utftext += String.fromCharCode((c >> 6) | 192);
                utftext += String.fromCharCode((c & 63) | 128);
            }
            else {
                utftext += String.fromCharCode((c >> 12) | 224);
                utftext += String.fromCharCode(((c >> 6) & 63) | 128);
                utftext += String.fromCharCode((c & 63) | 128);
            }

        }

        return utftext;
    };

    var x=Array();
    var k,AA,BB,CC,DD,a,b,c,d;
    var S11=7, S12=12, S13=17, S14=22;
    var S21=5, S22=9 , S23=14, S24=20;
    var S31=4, S32=11, S33=16, S34=23;
    var S41=6, S42=10, S43=15, S44=21;

    string = Utf8Encode(string);

    x = ConvertToWordArray(string);

    a = 0x67452301; b = 0xEFCDAB89; c = 0x98BADCFE; d = 0x10325476;

    for (k=0;k<x.length;k+=16) {
        AA=a; BB=b; CC=c; DD=d;
        a=FF(a,b,c,d,x[k+0], S11,0xD76AA478);
        d=FF(d,a,b,c,x[k+1], S12,0xE8C7B756);
        c=FF(c,d,a,b,x[k+2], S13,0x242070DB);
        b=FF(b,c,d,a,x[k+3], S14,0xC1BDCEEE);
        a=FF(a,b,c,d,x[k+4], S11,0xF57C0FAF);
        d=FF(d,a,b,c,x[k+5], S12,0x4787C62A);
        c=FF(c,d,a,b,x[k+6], S13,0xA8304613);
        b=FF(b,c,d,a,x[k+7], S14,0xFD469501);
        a=FF(a,b,c,d,x[k+8], S11,0x698098D8);
        d=FF(d,a,b,c,x[k+9], S12,0x8B44F7AF);
        c=FF(c,d,a,b,x[k+10],S13,0xFFFF5BB1);
        b=FF(b,c,d,a,x[k+11],S14,0x895CD7BE);
        a=FF(a,b,c,d,x[k+12],S11,0x6B901122);
        d=FF(d,a,b,c,x[k+13],S12,0xFD987193);
        c=FF(c,d,a,b,x[k+14],S13,0xA679438E);
        b=FF(b,c,d,a,x[k+15],S14,0x49B40821);
        a=GG(a,b,c,d,x[k+1], S21,0xF61E2562);
        d=GG(d,a,b,c,x[k+6], S22,0xC040B340);
        c=GG(c,d,a,b,x[k+11],S23,0x265E5A51);
        b=GG(b,c,d,a,x[k+0], S24,0xE9B6C7AA);
        a=GG(a,b,c,d,x[k+5], S21,0xD62F105D);
        d=GG(d,a,b,c,x[k+10],S22,0x2441453);
        c=GG(c,d,a,b,x[k+15],S23,0xD8A1E681);
        b=GG(b,c,d,a,x[k+4], S24,0xE7D3FBC8);
        a=GG(a,b,c,d,x[k+9], S21,0x21E1CDE6);
        d=GG(d,a,b,c,x[k+14],S22,0xC33707D6);
        c=GG(c,d,a,b,x[k+3], S23,0xF4D50D87);
        b=GG(b,c,d,a,x[k+8], S24,0x455A14ED);
        a=GG(a,b,c,d,x[k+13],S21,0xA9E3E905);
        d=GG(d,a,b,c,x[k+2], S22,0xFCEFA3F8);
        c=GG(c,d,a,b,x[k+7], S23,0x676F02D9);
        b=GG(b,c,d,a,x[k+12],S24,0x8D2A4C8A);
        a=HH(a,b,c,d,x[k+5], S31,0xFFFA3942);
        d=HH(d,a,b,c,x[k+8], S32,0x8771F681);
        c=HH(c,d,a,b,x[k+11],S33,0x6D9D6122);
        b=HH(b,c,d,a,x[k+14],S34,0xFDE5380C);
        a=HH(a,b,c,d,x[k+1], S31,0xA4BEEA44);
        d=HH(d,a,b,c,x[k+4], S32,0x4BDECFA9);
        c=HH(c,d,a,b,x[k+7], S33,0xF6BB4B60);
        b=HH(b,c,d,a,x[k+10],S34,0xBEBFBC70);
        a=HH(a,b,c,d,x[k+13],S31,0x289B7EC6);
        d=HH(d,a,b,c,x[k+0], S32,0xEAA127FA);
        c=HH(c,d,a,b,x[k+3], S33,0xD4EF3085);
        b=HH(b,c,d,a,x[k+6], S34,0x4881D05);
        a=HH(a,b,c,d,x[k+9], S31,0xD9D4D039);
        d=HH(d,a,b,c,x[k+12],S32,0xE6DB99E5);
        c=HH(c,d,a,b,x[k+15],S33,0x1FA27CF8);
        b=HH(b,c,d,a,x[k+2], S34,0xC4AC5665);
        a=II(a,b,c,d,x[k+0], S41,0xF4292244);
        d=II(d,a,b,c,x[k+7], S42,0x432AFF97);
        c=II(c,d,a,b,x[k+14],S43,0xAB9423A7);
        b=II(b,c,d,a,x[k+5], S44,0xFC93A039);
        a=II(a,b,c,d,x[k+12],S41,0x655B59C3);
        d=II(d,a,b,c,x[k+3], S42,0x8F0CCC92);
        c=II(c,d,a,b,x[k+10],S43,0xFFEFF47D);
        b=II(b,c,d,a,x[k+1], S44,0x85845DD1);
        a=II(a,b,c,d,x[k+8], S41,0x6FA87E4F);
        d=II(d,a,b,c,x[k+15],S42,0xFE2CE6E0);
        c=II(c,d,a,b,x[k+6], S43,0xA3014314);
        b=II(b,c,d,a,x[k+13],S44,0x4E0811A1);
        a=II(a,b,c,d,x[k+4], S41,0xF7537E82);
        d=II(d,a,b,c,x[k+11],S42,0xBD3AF235);
        c=II(c,d,a,b,x[k+2], S43,0x2AD7D2BB);
        b=II(b,c,d,a,x[k+9], S44,0xEB86D391);
        a=AddUnsigned(a,AA);
        b=AddUnsigned(b,BB);
        c=AddUnsigned(c,CC);
        d=AddUnsigned(d,DD);
    }

    var temp = WordToHex(a)+WordToHex(b)+WordToHex(c)+WordToHex(d);

    return temp.toLowerCase();
}

function validateSignUpForm(emptyFieldString, shortPasswordString, wrongRepeatedPasswordString, wrongTelString, wrongEmailString) {

    var emptyFieldString = " " + emptyFieldString;
    var shortPasswordString = " " + shortPasswordString;
    var wrongRepeatedPasswordString = " " + wrongRepeatedPasswordString;
    var wrongTelString = " " + wrongTelString;
    var wrongEmailString = " " + wrongEmailString;

  
    function firstNameValidation(emptyFieldString) {
      var firstNameLabel = document.forms["signUpForm"]["firstName"].value;
      if (firstNameLabel == "") {
        var firstNameLabel = document.getElementById("firstNameLabel").innerHTML;
        if (!firstNameLabel.includes(emptyFieldString)) {
          firstNameLabel += emptyFieldString;
          document.getElementById("firstNameLabel").innerHTML = firstNameLabel;
          document.getElementById("firstNameLabel").style.color = "red";
        }
        // return false;
      } else if (firstNameLabel !== "" && document.getElementById("firstNameLabel").innerHTML.includes(emptyFieldString)) {
        document.getElementById("firstNameLabel").innerHTML = document.getElementById("firstNameLabel").innerHTML.replace(emptyFieldString, "");
        document.getElementById("firstNameLabel").style.color = "green";
      } else {
        document.getElementById("firstNameLabel").style.color = "green";
      }
      return true;
    };

    function lastNameValidation(emptyFieldString) {
      var lastNameLabel = document.forms["signUpForm"]["lastName"].value;
      if (lastNameLabel == "") {
        var lastNameLabel = document.getElementById("lastNameLabel").innerHTML;
        if (!lastNameLabel.includes(emptyFieldString)) {
          lastNameLabel += emptyFieldString;
          document.getElementById("lastNameLabel").innerHTML = lastNameLabel;
          document.getElementById("lastNameLabel").style.color = "red";
        }
        return false;
      } else if (lastNameLabel !== "" && document.getElementById("lastNameLabel").innerHTML.includes(emptyFieldString)) {
        document.getElementById("lastNameLabel").innerHTML = document.getElementById("lastNameLabel").innerHTML.replace(emptyFieldString, "");
        document.getElementById("lastNameLabel").style.color = "green";
      } else {
        document.getElementById("lastNameLabel").style.color = "green";
      }
      return true;
    };

    function passwordValidation(shortPasswordString, wrongRepeatedPasswordString) {
      var requiredPasswordLength = 8;
      var password = document.forms["signUpForm"]["password"].value;
      if (password.length < requiredPasswordLength) {
        var passwordLabel = document.getElementById("passwordLabel").innerHTML;
        if (!passwordLabel.includes(shortPasswordString)) {
          passwordLabel += shortPasswordString;
          document.getElementById("passwordLabel").innerHTML = passwordLabel;
          document.getElementById("passwordLabel").style.color = "red";
        }
        return false;
      } else if (passwordLabel !== "" && document.getElementById("passwordLabel").innerHTML.includes(shortPasswordString)) {
        document.getElementById("passwordLabel").innerHTML = document.getElementById("passwordLabel").innerHTML.replace(shortPasswordString, "");
        document.getElementById("passwordLabel").style.color = "green";
      } else {
        document.getElementById("passwordLabel").style.color = "green";
      }
      var repeatedPassword = document.forms["signUpForm"]["repeatedPassword"].value;
      if (repeatedPassword !== password) {
        var repeatedPasswordLabel = document.getElementById("repeatedPasswordLabel").innerHTML;
        if (!repeatedPasswordLabel.includes(wrongRepeatedPasswordString)) {
          repeatedPasswordLabel += wrongRepeatedPasswordString;
          document.getElementById("repeatedPasswordLabel").innerHTML = repeatedPasswordLabel;
          document.getElementById("repeatedPasswordLabel").style.color = "red";
          return false;
        }
      } else if (repeatedPassword == password && document.getElementById("repeatedPasswordLabel").innerHTML.includes(wrongRepeatedPasswordString)) {
        document.getElementById("repeatedPasswordLabel").innerHTML = document.getElementById("repeatedPasswordLabel").innerHTML.replace(wrongRepeatedPasswordString, "");
        document.getElementById("repeatedPasswordLabel").style.color = "green";
      } else {
        document.getElementById("repeatedPasswordLabel").style.color = "green";
      }
      return true;
    };

    function telephoneNumberValidation(wrongTelString) {
      var telRegex = /^\+[0-9]{12}$/;
      var tel = document.forms["signUpForm"]["tel"].value;
      if (!telRegex.test(tel)) {
        var telLabel = document.getElementById("telLabel").innerHTML;
        if (!telLabel.includes(wrongTelString)) {
          telLabel += wrongTelString;
          document.getElementById("telLabel").innerHTML = telLabel;
          document.getElementById("telLabel").style.color = "red";
        }
        return false;
      } else if (telRegex.test(tel) && document.getElementById("telLabel").innerHTML.includes(wrongTelString)) {
        document.getElementById("telLabel").innerHTML = document.getElementById("telLabel").innerHTML.replace(wrongTelString, "");
        document.getElementById("telLabel").style.color = "green";
      } else {
        document.getElementById("telLabel").style.color = "green";
      }
      return true;
    };

    function emailValidation(wrongEmailString) {
      var emailRegex = /^(([^<>()\[\]\.,;:\s@\"]+(\.[^<>()\[\]\.,;:\s@\"]+)*)|(\".+\"))@(([^<>()[\]\.,;:\s@\"]+\.)+[^<>()[\]\.,;:\s@\"]{2,})$/i;
      var email = document.forms["signUpForm"]["email"].value;
      if (!emailRegex.test(email)) {
        var emailLabel = document.getElementById("telLabel").innerHTML;
        if (!emailLabel.includes(wrongEmailString)) {
          emailLabel += wrongEmailString;
          document.getElementById("emailLabel").innerHTML = emailLabel;
          document.getElementById("emailLabel").style.color = "red";
        }
        return false;
      } else if (emailRegex.test(email) && document.getElementById("emailLabel").innerHTML.includes(wrongEmailString)) {
        document.getElementById("emailLabel").innerHTML = document.getElementById("emailLabel").innerHTML.replace(wrongEmailString, "");
        document.getElementById("emailLabel").style.color = "green";
      } else {
        document.getElementById("emailLabel").style.color = "green";
      }
      return true;
    };

    function agreementValidation() {
      if (!document.getElementById("agreement").checked) {
        var agreementLabel = document.getElementById("agreementLabel").innerHTML;
        document.getElementById("agreementLabel").style.color = "red";
        return false;
      } else {
        document.getElementById("agreementLabel").style.color = "green";
      }
      return true;
    };

    var firstName = firstNameValidation(emptyFieldString);
    var lastName = lastNameValidation(emptyFieldString);
    var password = passwordValidation(shortPasswordString, wrongRepeatedPasswordString);
    var tel = telephoneNumberValidation(wrongTelString);
    var email = emailValidation(wrongEmailString);
    var agreement = agreementValidation();

    return (firstName && lastName && password && tel && email && agreement);
}

function validateSignInForm(wrongEmailString, shortPasswordString) {

    wrongEmailString = " " + wrongEmailString;
    shortPasswordString = " " + shortPasswordString;

    function emailValidation(wrongEmailString) {
        var emailRegex = /^(([^<>()\[\]\.,;:\s@\"]+(\.[^<>()\[\]\.,;:\s@\"]+)*)|(\".+\"))@(([^<>()[\]\.,;:\s@\"]+\.)+[^<>()[\]\.,;:\s@\"]{2,})$/i;
        var email = document.forms["signInForm"]["email"].value;
        if (!emailRegex.test(email)) {
            var email_signIn_label = document.getElementById("email_signIn_label").innerHTML;
            if (!email_signIn_label.includes(wrongEmailString)) {
                email_signIn_label += wrongEmailString;
                document.getElementById("email_signIn_label").innerHTML = email_signIn_label;
                document.getElementById("email_signIn_label").style.color = "red";
            }
            return false;
        } else if (emailRegex.test(email) && document.getElementById("email_signIn_label").innerHTML.includes(wrongEmailString)) {
            document.getElementById("email_signIn_label").innerHTML = document.getElementById("email_signIn_label").innerHTML.replace(wrongEmailString, "");
            document.getElementById("email_signIn_label").style.color = "green";
        } else {
            document.getElementById("email_signIn_label").style.color = "green";
        }
        return true;
    };

    function passwordValidation(shortPasswordString) {
        var requiredPasswordLength = 8;
        var password = document.forms["signInForm"]["password"].value;
        if (password.length < requiredPasswordLength) {
            var password_signIn_label = document.getElementById("password_signIn_label").innerHTML;
            if (!password_signIn_label.includes(shortPasswordString)) {
                password_signIn_label += shortPasswordString;
                document.getElementById("password_signIn_label").innerHTML = password_signIn_label;
                document.getElementById("password_signIn_label").style.color = "red";
            }
            return false;
        } else if (password_signIn_label !== "" && document.getElementById("password_signIn_label").innerHTML.includes(shortPasswordString)) {
            document.getElementById("password_signIn_label").innerHTML = document.getElementById("password_signIn_label").innerHTML.replace(shortPasswordString, "");
            document.getElementById("password_signIn_label").style.color = "green";
        } else {
            document.getElementById("password_signIn_label").style.color = "green";
        }
        return true;
    };

    var email = emailValidation(wrongEmailString);
    var password = passwordValidation(shortPasswordString);

    return email && password;
}

function changePasswordForm(shortPasswordString) {
    shortPasswordString = " " + shortPasswordString;
    function passwordValidation(shortPasswordString) {
        var requiredPasswordLength = 8;
        var password = document.forms["changePasswordForm"]["password"].value;
        if (password.length < requiredPasswordLength) {
            var passwordLabel = document.getElementById("passwordLabel").innerHTML;
            if (!passwordLabel.includes(shortPasswordString)) {
                passwordLabel += shortPasswordString;
                document.getElementById("passwordLabel").innerHTML = passwordLabel;
                document.getElementById("passwordLabel").style.color = "red";
            }
            return false;
        } else if (passwordLabel !== "" && document.getElementById("passwordLabel").innerHTML.includes(shortPasswordString)) {
            document.getElementById("passwordLabel").innerHTML = document.getElementById("passwordLabel").innerHTML.replace(shortPasswordString, "");
            document.getElementById("passwordLabel").style.color = "green";
        } else {
            document.getElementById("passwordLabel").style.color = "green";
        }
        return true;
    };

    function newPasswordValidation(shortPasswordString) {
        var requiredPasswordLength = 8;
        var new_password = document.forms["changePasswordForm"]["new_password"].value;
        if (new_password.length < requiredPasswordLength) {
            var new_passwordLabel = document.getElementById("new_passwordLabel").innerHTML;
            if (!new_passwordLabel.includes(shortPasswordString)) {
                new_passwordLabel += shortPasswordString;
                document.getElementById("new_passwordLabel").innerHTML = new_passwordLabel;
                document.getElementById("new_passwordLabel").style.color = "red";
            }
            return false;
        } else if (new_passwordLabel !== "" && document.getElementById("new_passwordLabel").innerHTML.includes(shortPasswordString)) {
            document.getElementById("new_passwordLabel").innerHTML = document.getElementById("new_passwordLabel").innerHTML.replace(shortPasswordString, "");
            document.getElementById("new_passwordLabel").style.color = "green";
        } else {
            document.getElementById("new_passwordLabel").style.color = "green";
        }
        return true;
    };

    var password = passwordValidation(shortPasswordString);
    var new_password = newPasswordValidation(shortPasswordString);

    return password && new_password;
}

function validateEditUser(emptyFieldString, wrongTelString, wrongEmailString, number) {

    emptyFieldString = " " + emptyFieldString;
    wrongTelString = " " + wrongTelString;
    wrongEmailString = " " + wrongEmailString;

    function firstNameValidation(emptyFieldString, number) {
        var firstNameLabel = document.forms["editUserForm-" + number]["firstName"].value;
        if (firstNameLabel == "") {
            var firstNameLabel = document.getElementById("firstNameLabel-" + number).innerHTML;
            if (!firstNameLabel.includes(emptyFieldString)) {
                firstNameLabel += emptyFieldString;
                document.getElementById("firstNameLabel-" + number).innerHTML = firstNameLabel;
                document.getElementById("firstNameLabel-" + number).style.color = "red";
            }
            return false;
        } else if (firstNameLabel !== "" && document.getElementById("firstNameLabel-" + number).innerHTML.includes(emptyFieldString)) {
            document.getElementById("firstNameLabel-" + number).innerHTML = document.getElementById("firstNameLabel-" + number).innerHTML.replace(emptyFieldString, "");
            document.getElementById("firstNameLabel-" + number).style.color = "green";
        } else {
            document.getElementById("firstNameLabel-" + number).style.color = "green";
        }
        return true;
    };

    function lastNameValidation(emptyFieldString, number) {
        var lastNameLabel = document.forms["editUserForm-" + number]["lastName"].value;
        if (lastNameLabel == "") {
            var lastNameLabel = document.getElementById("lastNameLabel-" + number).innerHTML;
            if (!lastNameLabel.includes(emptyFieldString)) {
                lastNameLabel += emptyFieldString;
                document.getElementById("lastNameLabel-" + number).innerHTML = lastNameLabel;
                document.getElementById("lastNameLabel-" + number).style.color = "red";
            }
            return false;
        } else if (lastNameLabel !== "" && document.getElementById("lastNameLabel-" + number).innerHTML.includes(emptyFieldString)) {
            document.getElementById("lastNameLabel-" + number).innerHTML = document.getElementById("lastNameLabel-" + number).innerHTML.replace(emptyFieldString, "");
            document.getElementById("lastNameLabel-" + number).style.color = "green";
        } else {
            document.getElementById("lastNameLabel-" + number).style.color = "green";
        }
        return true;
    };

    function telephoneNumberValidation(wrongTelString, number) {
        var telRegex = /^\+[0-9]{12}$/;
        var tel = document.forms["editUserForm-" + number]["tel"].value;
        if (!telRegex.test(tel)) {
            var telLabel = document.getElementById("telLabel-" + number).innerHTML;
            if (!telLabel.includes(wrongTelString)) {
                telLabel += wrongTelString;
                document.getElementById("telLabel-" + number).innerHTML = telLabel;
                document.getElementById("telLabel-" + number).style.color = "red";
            }
            return false;
        } else if (telRegex.test(tel) && document.getElementById("telLabel-" + number).innerHTML.includes(wrongTelString)) {
            document.getElementById("telLabel-" + number).innerHTML = document.getElementById("telLabel-" + number).innerHTML.replace(wrongTelString, "");
            document.getElementById("telLabel-" + number).style.color = "green";
        } else {
            document.getElementById("telLabel-" + number).style.color = "green";
        }
        return true;
    };

    function emailValidation(wrongEmailString, number) {
        var emailRegex = /^(([^<>()\[\]\.,;:\s@\"]+(\.[^<>()\[\]\.,;:\s@\"]+)*)|(\".+\"))@(([^<>()[\]\.,;:\s@\"]+\.)+[^<>()[\]\.,;:\s@\"]{2,})$/i;
        var email = document.forms["editUserForm-" + number]["email"].value;
        if (!emailRegex.test(email)) {
            var emailLabel = document.getElementById("emailLabel-" + number).innerHTML;
            if (!emailLabel.includes(wrongEmailString)) {
                emailLabel += wrongEmailString;
                document.getElementById("emailLabel-" + number).innerHTML = emailLabel;
                document.getElementById("emailLabel-" + number).style.color = "red";
            }
            return false;
        } else if (emailRegex.test(email) && document.getElementById("emailLabel-" + number).innerHTML.includes(wrongEmailString)) {
            document.getElementById("emailLabel-" + number).innerHTML = document.getElementById("emailLabel-" + number).innerHTML.replace(wrongEmailString, "");
            document.getElementById("emailLabel-" + number).style.color = "green";
        } else {
            document.getElementById("emailLabel-" + number).style.color = "green";
        }
        return true;
    };

    var firstName = firstNameValidation(emptyFieldString, number);
    var lastName = lastNameValidation(emptyFieldString, number);
    var tel = telephoneNumberValidation(wrongTelString, number);
    var email = emailValidation(wrongEmailString, number);

    return (firstName && lastName && tel && email);
}

function validateEditPassword(shortPasswordString, number) {
    shortPasswordString = " " + shortPasswordString;
    var requiredPasswordLength = 8;
    var new_password = document.forms["editPasswordForm-" + number]["new_password"].value;
    if (new_password.length < requiredPasswordLength) {
        var newPasswordLabel = document.getElementById("newPasswordLabel-" + number).innerHTML;
        if (!newPasswordLabel.includes(shortPasswordString)) {
            newPasswordLabel += shortPasswordString;
            document.getElementById("newPasswordLabel-" + number).innerHTML = newPasswordLabel;
            document.getElementById("newPasswordLabel-" + number).style.color = "red";
        }
        return false;
    } else if (newPasswordLabel !== "" && document.getElementById("newPasswordLabel-" + number).innerHTML.includes(shortPasswordString)) {
        document.getElementById("newPasswordLabel-" + number).innerHTML = document.getElementById("newPasswordLabel-" + number).innerHTML.replace(shortPasswordString, "");
        document.getElementById("newPasswordLabel-" + number).style.color = "green";
    } else {
        document.getElementById("newPasswordLabel-" + number).style.color = "green";
    }
    return true;
};

function validateEditProfile(emptyFieldString, wrongTelString, wrongEmailString) {

    emptyFieldString = " " + emptyFieldString;
    wrongTelString = " " + wrongTelString;
    wrongEmailString = " " + wrongEmailString

    function firstNameValidation(emptyFieldString) {
        var firstNameLabel = document.forms["editProfileForm"]["firstName"].value;
        if (firstNameLabel == "") {
            var firstNameLabel = document.getElementById("firstNameLabel").innerHTML;
            if (!firstNameLabel.includes(emptyFieldString)) {
                firstNameLabel += emptyFieldString;
                document.getElementById("firstNameLabel").innerHTML = firstNameLabel;
                document.getElementById("firstNameLabel").style.color = "red";
            }
            return false;
        } else if (firstNameLabel !== "" && document.getElementById("firstNameLabel").innerHTML.includes(emptyFieldString)) {
            document.getElementById("firstNameLabel").innerHTML = document.getElementById("firstNameLabel").innerHTML.replace(emptyFieldString, "");
            document.getElementById("firstNameLabel").style.color = "green";
        } else {
            document.getElementById("firstNameLabel").style.color = "green";
        }
        return true;
    };

    function lastNameValidation(emptyFieldString) {
        var lastNameLabel = document.forms["editProfileForm"]["lastName"].value;
        if (lastNameLabel == "") {
            var lastNameLabel = document.getElementById("lastNameLabel").innerHTML;
            if (!lastNameLabel.includes(emptyFieldString)) {
                lastNameLabel += emptyFieldString;
                document.getElementById("lastNameLabel").innerHTML = lastNameLabel;
                document.getElementById("lastNameLabel").style.color = "red";
            }
            return false;
        } else if (lastNameLabel !== "" && document.getElementById("lastNameLabel").innerHTML.includes(emptyFieldString)) {
            document.getElementById("lastNameLabel").innerHTML = document.getElementById("lastNameLabel").innerHTML.replace(emptyFieldString, "");
            document.getElementById("lastNameLabel").style.color = "green";
        } else {
            document.getElementById("lastNameLabel").style.color = "green";
        }
        return true;
    };

    function telephoneNumberValidation(wrongTelString) {
        var telRegex = /^\+[0-9]{12}$/;
        var tel = document.forms["editProfileForm"]["tel"].value;
        if (!telRegex.test(tel)) {
            var telLabel = document.getElementById("telLabel").innerHTML;
            if (!telLabel.includes(wrongTelString)) {
                telLabel += wrongTelString;
                document.getElementById("telLabel").innerHTML = telLabel;
                document.getElementById("telLabel").style.color = "red";
            }
            return false;
        } else if (telRegex.test(tel) && document.getElementById("telLabel").innerHTML.includes(wrongTelString)) {
            document.getElementById("telLabel").innerHTML = document.getElementById("telLabel").innerHTML.replace(wrongTelString, "");
            document.getElementById("telLabel").style.color = "green";
        } else {
            document.getElementById("telLabel").style.color = "green";
        }
        return true;
    };

    function emailValidation(wrongEmailString) {
        var emailRegex = /^(([^<>()\[\]\.,;:\s@\"]+(\.[^<>()\[\]\.,;:\s@\"]+)*)|(\".+\"))@(([^<>()[\]\.,;:\s@\"]+\.)+[^<>()[\]\.,;:\s@\"]{2,})$/i;
        var email = document.forms["editProfileForm"]["email"].value;
        if (!emailRegex.test(email)) {
            var emailLabel = document.getElementById("emailLabel").innerHTML;
            if (!emailLabel.includes(wrongEmailString)) {
                emailLabel += wrongEmailString;
                document.getElementById("emailLabel").innerHTML = emailLabel;
                document.getElementById("emailLabel").style.color = "red";
            }
            return false;
        } else if (emailRegex.test(email) && document.getElementById("emailLabel").innerHTML.includes(wrongEmailString)) {
            document.getElementById("emailLabel").innerHTML = document.getElementById("emailLabel").innerHTML.replace(wrongEmailString, "");
            document.getElementById("emailLabel").style.color = "green";
        } else {
            document.getElementById("emailLabel").style.color = "green";
        }
        return true;
    };

    var firstName = firstNameValidation(emptyFieldString);
    var lastName = lastNameValidation(emptyFieldString);
    var tel = telephoneNumberValidation(wrongTelString);
    var email = emailValidation(wrongEmailString);

    return firstName && lastName && tel && email;
}

function slidePrevious(folderPath) {
    var img = new Image();
    document.getElementById("imgsrc").innerHTML--;
    if (document.getElementById("imgsrc").innerHTML <= 1) {
        $('#next-button').show();
        $('#previous-button').hide();
    } else {
        $('#next-button').show();
    }
    img.src = folderPath + document.getElementById("imgsrc").innerHTML + '.jpg';
    img.onload = function() {
        document.getElementById("my_slider").innerHTML = '<img src="' + img.src + '" alt="room" style="height:500px;" id="image">';
    };
};

function slideNext(folderPath) {
    var img = new Image();
    document.getElementById("imgsrc").innerHTML++;
    img.src = folderPath + document.getElementById("imgsrc").innerHTML + '.jpg';
    img.onload = function() {
        document.getElementById("my_slider").innerHTML = '<img src="' + this.src + '" alt="room" style="height:500px;" id="image">';
        if (document.getElementById("imgsrc").innerHTML > 1) {
            $('#previous-button').show();
        }
        var forNext = document.getElementById("imgsrc").innerHTML;
        forNext++;
        var img = new Image();
        img.src = folderPath + forNext + '.jpg';
        img.onerror = function() {
            $('#next-button').hide();
        }
    };
};
