// Function to handle login form submission
function login(event) {
    event.preventDefault(); // Prevent form submission

    // Get the entered username and password
    var username = document.getElementById("uemail").value;
    var password = document.getElementById("upassword").value;

    // Hardcoded username and password (replace with actual credentials)
    var hardcodedUsername = "admin";
    var hardcodedPassword = "password";

    // Check if the entered username and password match the hardcoded credentials
    if (username === hardcodedUsername && password === hardcodedPassword) {
        // If credentials match, redirect to the dashboard or perform desired action
        console.log("Login successful");
        // Replace the next line with the redirection code
        window.location.href = "/home.html";
    } else {
        // If credentials do not match, display error message
        console.log("Login failed");
    }
}
