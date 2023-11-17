const fullscreenImages = document.querySelectorAll('img');

// Function to enter full-screen
function enterFullscreen(element) {
    if (element.requestFullscreen) {
        element.requestFullscreen();
    } else if (element.mozRequestFullScreen) {
        element.mozRequestFullScreen(); // Firefox
    } else if (element.webkitRequestFullscreen) {
        element.webkitRequestFullscreen(); // Chrome, Safari, and Opera
    } else if (element.msRequestFullscreen) {
        element.msRequestFullscreen(); // Internet Explorer
    }
}

// Function to exit full-screen
function exitFullscreen() {
    if (document.exitFullscreen) {
        document.exitFullscreen();
    } else if (document.mozCancelFullScreen) {
        document.mozCancelFullScreen(); // Firefox
    } else if (document.webkitExitFullscreen) {
        document.webkitExitFullscreen(); // Chrome, Safari, and Opera
    } else if (document.msExitFullscreen) {
        document.msExitFullscreen(); // Internet Explorer
    }
}

// Toggle full-screen mode when images are clicked
fullscreenImages.forEach((img) => {
    img.addEventListener('click', () => {
        if (!document.fullscreenElement) {
            enterFullscreen(img);
        } else {
            exitFullscreen();
        }
    });
});