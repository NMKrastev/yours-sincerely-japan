function validateRegistrationForm(event) {

    let isValid = true;

    let fullNameInput = document.getElementById('fullName');
    let fullNameValue = fullNameInput.value.trim();
    let fullNameAlert = document.getElementById('full-name-alert');
    let smallFullName = document.getElementById('full-name-error');

    if (!smallFullName) {
        smallFullName = document.createElement('small');
        smallFullName.setAttribute('id', 'full-name-error');
    }

    fullNameInput.classList.remove('is-invalid');
    fullNameAlert.innerHTML = '';

    if (fullNameValue.length < 1 || fullNameValue.length > 30) {
        isValid = false;
        smallFullName.textContent = 'Full name must be between 1 and 30 characters.';
        fullNameInput.classList.add('is-invalid');
        fullNameAlert.appendChild(smallFullName);
        event.preventDefault();
    }

    let emailInput = document.getElementById('email');
    let emailValue = emailInput.value.trim().toLowerCase();
    let emailAlert = document.getElementById('email-alert');
    let emailRegex = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
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

    let confirmPasswordInput = document.getElementById('confirmPassword');
    let confirmPasswordValue = confirmPasswordInput.value;
    let confirmPasswordAlert = document.getElementById('confirm-password-alert');
    let smallConfirmPassword = document.getElementById('confirm-password-error');

    if (!smallConfirmPassword) {
        smallConfirmPassword = document.createElement('small');
        smallConfirmPassword.setAttribute('id', 'confirm-password-error');
    }

    confirmPasswordInput.classList.remove('is-invalid');
    confirmPasswordAlert.innerHTML = '';

    if (passwordValue !== confirmPasswordValue) {
        isValid = false;
        smallConfirmPassword.textContent = 'Passwords do not match.';
        confirmPasswordInput.classList.add('is-invalid');
        confirmPasswordAlert.appendChild(smallConfirmPassword);
        event.preventDefault();
    }

    if (isValid) {
        return true;
    }

    event.preventDefault();
}

document.getElementById('registration-form').addEventListener('submit', validateRegistrationForm);