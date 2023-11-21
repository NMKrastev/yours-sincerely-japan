function validateRegistrationForm(event) {

let isValid = true;

let fullNameInput = document.getElementById('fullName');
let fullNameValue = fullNameInput.value.trim();
let fullNameAlert = document.getElementById('fullName-alert');
let smallFullName = document.getElementById('fullName-error');

if (!smallFullName) {
    smallFullName = document.createElement('small');
    smallFullName.setAttribute('id', 'fullName-error');
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
let emailAlert = document.getElementById('contact-email-alert');
let emailRegex = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
let smallEmail = document.getElementById('contact-email-error');

if (!smallEmail) {
    smallEmail = document.createElement('small');
    smallEmail.setAttribute('id', 'contact-email-error');
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

    let contactContentInput = document.getElementById('contactContent');
    let contactContentValue = contactContentInput.value.trim();
    let contactContentAlert = document.getElementById('contact-content-alert');
    let contactContentError = document.getElementById('contact-content-error');

    if (!contactContentError) {
        contactContentError = document.createElement('small');
        contactContentError.setAttribute('id', 'contact-content-error');
    }

    contactContentInput.classList.remove('is-invalid');
    contactContentAlert.innerHTML = '';

    if (contactContentValue.length < 1) {
        isValid = false;
        contactContentError.textContent = 'Your message must not be empty.';
        contactContentInput.classList.add('is-invalid');
        contactContentAlert.appendChild(contactContentError);
        event.preventDefault();
    }

    if (isValid) {
        return true;
    }

    event.preventDefault();
}

document.getElementById('contact-form').addEventListener('submit', validateRegistrationForm);