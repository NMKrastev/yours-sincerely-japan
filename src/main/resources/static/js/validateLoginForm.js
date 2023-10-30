function validateLoginForm(event) {

    let isValid = true;

    let emailInput = document.getElementById('email');
    let emailValue = emailInput.value.trim();
    let emailAlert = document.getElementById('email-alert');
    let emailRegex = /^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^-]+(?:\.[a-zA-Z0-9_!#$%&’*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/;
    let smallEmail = document.getElementById('email-error');

    if (!smallEmail) {
        smallEmail = document.createElement('small');
        smallEmail.setAttribute('id', 'email-error');
    }

    emailInput.classList.remove('is-invalid');
    emailAlert.innerHTML = '';

    if (!emailRegex.test(emailValue)) {
        isValid = false;
        smallEmail.textContent = 'Enter a valid email address.';
        emailInput.classList.add('is-invalid');
        emailAlert.appendChild(smallEmail);
        event.preventDefault();
    }

    let passwordInput = document.getElementById('password');
    let passwordValue = passwordInput.value;
    let passwordAlert = document.getElementById('password-alert');
    let smallPassword = document.getElementById('password-error');

    if (!smallPassword) {
        smallPassword = document.createElement('small');
        smallPassword.setAttribute('id', 'password-error');
    }

    passwordInput.classList.remove('is-invalid');
    passwordAlert.innerHTML = '';

    if (passwordValue.length < 8) {
        isValid = false;
        smallPassword.textContent = 'Password must be at least 8 characters long.';
        passwordInput.classList.add('is-invalid');
        passwordAlert.appendChild(smallPassword);
        event.preventDefault();
        return;
    } else if (passwordValue.length > 20) {
        isValid = false;
        smallPassword.textContent = 'Password must not be longer than 20 characters.';
        passwordInput.classList.add('is-invalid');
        passwordAlert.appendChild(smallPassword);
        event.preventDefault();
        return;
    }

    let passwordPattern = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#%&$*])/;

    if (!passwordPattern.test(passwordValue)) {
        isValid = false;
        passwordAlert.innerHTML =
            'Password must contain at least:<br>' +
            '- One lower case<br>' +
            '- One uppercase<br>' +
            '- One number<br>' +
            '- One special symbol: !, @, #, %, &, $, *';
        passwordInput.classList.add('is-invalid');
        event.preventDefault();
    }

    if (isValid) {
        return true;
    }

    event.preventDefault();
}

document.getElementById('login-form').addEventListener('submit', validateLoginForm);