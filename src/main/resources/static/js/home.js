let inwardBtn = document.getElementById("inwardBtn");
let outwardBtn = document.getElementById("outwardBtn");
let searchBtn = document.getElementById("searchBtn");
let toast = document.getElementById("toast");

inwardBtn.addEventListener('click', function() {
    document.getElementById('outward').classList.remove('active');
    document.getElementById('inward').classList.add('active');
    document.getElementById('search').classList.remove('active');
    outwardBtn.classList.remove('btn-active');
    inwardBtn.classList.add('btn-active');
    searchBtn.classList.remove('btn-active');
});

outwardBtn.addEventListener('click', function() {
    outwardBtn.classList.add('btn-active');
    inwardBtn.classList.remove('btn-active');
    searchBtn.classList.remove('btn-active');
    document.getElementById('inward').classList.remove('active');
    document.getElementById('outward').classList.add('active');
    document.getElementById('search').classList.remove('active');
});

searchBtn.addEventListener('click',function(){

    document.getElementById('outward').classList.remove('active');
    document.getElementById('inward').classList.remove('active');
    document.getElementById('search').classList.add('active');
    outwardBtn.classList.remove('btn-active');
    inwardBtn.classList.remove('btn-active');
    searchBtn.classList.add('btn-active');
});

//////////////////////////INWARD//////////////////////////////////////////////
document.getElementById("inwardDozen").addEventListener("input", function() {
        var val = document.getElementById("inwardDozen").value;
        if(!val) return;
        var dozenValue = parseInt(val);
        var pieceValue = dozenValue * 12;
        document.getElementById("inwardPiece").value = pieceValue;
});
document.getElementById("inwardPiece").addEventListener("input", function() {
        var val = document.getElementById("inwardDozen").value;
        if(!val) return;
        var dozenValue = parseInt(val);
        var pieceValue = Math.round(dozenValue/12 * 10 ** 2) / 10 ** 2;
        document.getElementById("inwardDozen").value = pieceValue;
});
document.getElementById('inwardForm').addEventListener('submit', function(event) {
    event.preventDefault(); // Prevent default form submission

    // Get form data
    var formData = new FormData(document.getElementById('inwardForm'));
    var inwardMemoNumber = document.getElementById('inwardMemoNumber').value;
    var inwardDate = document.getElementById('inwardDate').value ;
    var inwardItemSize = formData.get('inwardItemSize');
    var inwardItemType = formData.get('inwardItemType');
    var inwardDozen = formData.get('inwardDozen');
    var inwardPiece = formData.get('inwardPiece');

    // Create a new table row
    var newRow = document.createElement('tr');

    // Populate the new row with table data
    newRow.innerHTML = `
        <th scope="row">${document.querySelector(".inward-table-body").children.length + 1}</th>
        <td>${inwardMemoNumber}</td>
        <td>${inwardDate}</td>
        <td>${inwardItemSize}</td>
        <td>${inwardItemType}</td>
        <td>${inwardDozen}</td>
        <td>${inwardPiece}</td>
    `;

    // Append the new row to the end of the table body
    document.querySelector(".inward-table-body").appendChild(newRow);

    // Log the form data
    console.log(newRow);

    // Convert form data to JSON
    var jsonObject = {};
    jsonObject["inwardMemoNumber"] = inwardMemoNumber;
    jsonObject["inwardDate"] = inwardDate;

    formData.forEach(function(value, key){
        jsonObject[key] = value;
    });

    console.log(jsonObject);
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
        return;
    })
    .catch(error => {
        console.error('Error:', error);
        return;
    });
    const toastBootstrap = bootstrap.Toast.getOrCreateInstance(toast);
    toastBootstrap.show();
});
document.getElementById("addNewInwardItem").addEventListener('click', function(){
    document.getElementById('inwardForm').reset();
    document.getElementById('inwardMemoForm').reset();
    var tableBody = document.querySelector('.inward-table-body');
    tableBody.innerHTML = '';
});
///////////////////////////////////INWARD END/////////////////////////////////////////

////////////////////////////////////OUTWARD///////////////////////////////////////
document.getElementById("outwardDozen").addEventListener("input", function() {
        var val = document.getElementById("outwardDozen").value;
        if(!val) return;
        var dozenValue = parseInt(val);
        var pieceValue = dozenValue * 12;
        document.getElementById("outwardPiece").value = pieceValue;
});
document.getElementById("outwardPiece").addEventListener("input", function() {
        var val = document.getElementById("outwardDozen").value;
        if(!val) return;
        var dozenValue = parseInt(val);
        var pieceValue = Math.round(dozenValue/12 * 10 ** 2) / 10 ** 2;
        document.getElementById("outwardDozen").value = pieceValue;
});
document.getElementById('outwardForm').addEventListener('submit', function(event) {
    event.preventDefault(); // Prevent default form submission

    // Get form data
    var formData = new FormData(document.getElementById('outwardForm'));
    var outwardBailNumber = document.getElementById('outwardBailNumber').value;
    var outwardDate = document.getElementById('outwardDate').value ;
    var outwardItemSize = formData.get('outwardItemSize');
    var outwardItemType = formData.get('outwardItemType');
    var outwardDozen = formData.get('outwardDozen');
    var outwardPiece = formData.get('outwardPiece');

    // Create a new table row
    var newRow = document.createElement('tr');

    // Populate the new row with table data
    newRow.innerHTML = `
        <th scope="row">${document.querySelector(".outward-table-body").children.length + 1}</th>
        <td>${outwardBailNumber}</td>
        <td>${outwardDate}</td>
        <td>${outwardItemSize}</td>
        <td>${outwardItemType}</td>
        <td>${outwardDozen}</td>
        <td>${outwardPiece}</td>
    `;

    // Append the new row to the end of the table body
    document.querySelector(".outward-table-body").appendChild(newRow);

    // Log the form data
    console.log(newRow);

    // Convert form data to JSON
    var jsonObject = {};
    jsonObject["outwardBailNumber"] = outwardBailNumber;
    jsonObject["outwardDate"] = outwardDate;

    formData.forEach(function(value, key){
        jsonObject[key] = value;
    });

    console.log(jsonObject);

    fetch('/addOutward', {
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
        console.log('Outward data submitted successfully');
    })
    .catch(error => {
        console.error('Error:', error);
    });
    const toastBootstrap = bootstrap.Toast.getOrCreateInstance(toast);
    toastBootstrap.show();
});
document.getElementById("addNewOutwardItem").addEventListener('click', function(){
    document.getElementById('outwardForm').reset();
    document.getElementById('outwardBailForm').reset();
    var tableBody = document.querySelector('.outward-table-body');
    tableBody.innerHTML = '';
});
////////////////////////////////OUTWARD END//////////////////////////////////////////

//////////////////////////////SEARCH//////////////////////////////////////////////////

document.getElementById("searchForm").addEventListener('submit',function(event){
    event.preventDefault();
    let searchItemSize = document.getElementById("searchItemSize");
    let searchItemType = document.getElementById("searchItemType");

    var jsonObject = {};
    jsonObject["itemSize"] = searchItemSize.value;
    jsonObject["itemType"] = searchItemType.value;

    console.log(jsonObject);
    fetch('/searchItem', {
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
        return response.json();
        console.log('Search successfully');
    })
    .then(inwards => {
        console.log(inwards);
        inwards.forEach(inward =>{
            var newRow = document.createElement('tr');
            newRow.innerHTML = `<th scope="row">${inward.inwardId}</th>
                              <td>${inward.inwardMemoNumber}</td>
                              <td>${inward.inwardDate}</td>
                              <td>${inward.inwardItemSize}</td>
                              <td>${inward.inwardItemType}</td>
                              <td>${inward.inwardDozen}</td>
                              <td>${inward.inwardPiece}</td>`;
            document.querySelector(".search-table-body").appendChild(newRow);
        })
    })
    .catch(error => {
        console.error('Error:', error);
    });
});

//////////////////////////////SEARCH END///////////////////////////////////////////////

//document.getElementById('addProductBtn').addEventListener('click', function() {
//    var existingForm = document.getElementById('originalForm');
//    var clonedFormContainer = document.getElementById('clonedForm');
//    var cloneForm = existingForm.cloneNode(true); // Clone the form
//    var inputs = cloneForm.getElementsByTagName('input');
//    for (var i = 0; i < inputs.length; i++) {
//        inputs[i].value = '';
//    }
//    clonedFormContainer.appendChild(cloneForm);
//    clonedFormContainer.style.display = 'block';
//});
