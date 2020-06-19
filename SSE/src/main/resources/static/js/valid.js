  function checkPassword(str)
        {
            const re = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{12,}$/;
            return re.test(str);
        }

        function checkForm(form)
        {
            if(form.username.value == "") {
                alert("Error: Username cannot be blank!");
                form.username.focus();
                return false;
            }
            let re = /^[0-9]+$/;
            if(!re.test(form.username.value)) {
                alert("Error: Username must contain only numbers!");
                form.username.focus();
                return false;
            }
            if(form.password.value != "") {
                if(!checkPassword(form.password.value)) {
                    alert("The password you have entered is not valid!");
                    form.password.focus();
                    return false;
                }
            }
            return true;
        }
