document.getElementById("inwardDozen").addEventListener("input", function() {
        var dozenValue = parseInt(document.getElementById("inwardDozen").value);
        var pieceValue = dozenValue * 12;
        document.getElementById("inwardPiece").value = pieceValue;
});
document.getElementById("inwardPiece").addEventListener("input", function() {
        var dozenValue = parseInt(document.getElementById("inwardPiece").value);
        var pieceValue = Math.round(dozenValue/12 * 10 ** 2) / 10 ** 2;
        document.getElementById("inwardDozen").value = pieceValue;
});
document.getElementById('inwardForm').addEventListener('submit', function(event) {
        event.preventDefault(); // Prevent default form submission

        // Get form data
        var formData = new FormData(document.getElementById('inwardForm'));
        var inwardMemoNumber =  document.getElementById('inwardMemoNumber');
        var inwardDate = document.getElementById('inwardDate');
        // Convert formData to JSON object
        var jsonObject = {};
        jsonObject["inwardMemoNumber"] = inwardMemoNumber.value;
        jsonObject["inwardDate"] = inwardDate.value;
        formData.forEach(function(value, key){
            jsonObject[key] = value;
        });


        // Make AJAX request to Spring Boot controller
        console.log(JSON.stringify(jsonObject));
        fetch('/addInward', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(jsonObject)
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            console.log('Inward data submitted successfully');
        })
        .catch(error => {
            console.error('Error:', error);
        });
});

document.getElementById('addProductBtn').addEventListener('click', function() {
    var existingForm = document.getElementById('originalForm');
    var clonedFormContainer = document.getElementById('clonedForm');
    var cloneForm = existingForm.cloneNode(true); // Clone the form
    var inputs = cloneForm.getElementsByTagName('input'); // Get all input elements in the cloned form

    // Reset input values
    for (var i = 0; i < inputs.length; i++) {
        inputs[i].value = '';
    }
    clonedFormContainer.appendChild(cloneForm); // Append the cloned form to the container
    clonedFormContainer.style.display = 'block';
    // Insert the cloned form just below the existing form
//    existingForm.parentNode.insertBefore(cloneForm, existingForm.nextSibling);

});
