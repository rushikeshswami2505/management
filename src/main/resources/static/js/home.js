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

//////////////////////GET ITEM LIST ON LOAD PAGE////////////////////
let fullItemList;

// Call the function to fetch items list
getItemsList();

function getItemsList() {
    fetch('/getItemsList')
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            fullItemList = data;
        })
        .catch(error => {
            console.error('There was a problem with the fetch operation:', error);
        });
}
///////////////////////////Modal//////////////////
document.getElementById("itemForm").addEventListener('submit', function(event) {
    event.preventDefault();

    var formData = new FormData(document.getElementById("itemForm"));
    var itemSize = formData.get('itemSize');
    var itemType = formData.get('itemType');

    if (!itemSize || !itemType) {
        customToast("Please enter valid data", 0);
        return;
    }

    var jsonObject = {
        itemsId: {
            itemSize: itemSize,
            itemType: itemType
        }
    };

    fetch('/addItem', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(jsonObject)
    })
    .then(response => {
        if (!response.ok) {
            customToast("Error due to network", 0);
            throw new Error('Network response was not ok');
        }
        customToast("New Item Added", 1);
        document.getElementById("closeItemModel").click();
        getItemsList(); // Assuming this function exists to update the items list
    })
    .catch(error => {
        console.error('Error:', error);
    });
});


//////////////////////////INWARD//////////////////////////////////////////////
document.getElementById("inwardDozen").addEventListener("input", function() {
        var val = document.getElementById("inwardDozen").value;
        if(!val) return;
        var dozenValue = parseFloat(val);
        var pieceValue = dozenValue * 12;
        document.getElementById("inwardPiece").value = pieceValue;
});
document.getElementById("inwardPiece").addEventListener("input", function() {
        var val = document.getElementById("inwardPiece").value;
        if(!val) return;
        var dozenValue = parseFloat(val);
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
    if(!inwardMemoNumber || !inwardDate || !inwardItemSize || !inwardItemType || !inwardDozen || !inwardPiece){
        customToast("Please enter valid data",0);
        return;
    }

    let checkInwardContain = false;
    if (fullItemList) { // Ensure fullItemList is not null or undefined
        for (var i = 0; i < fullItemList.length; i++) {
            if (fullItemList[i].itemsId.itemSize == inwardItemSize && fullItemList[i].itemsId.itemType === inwardItemType) {
                checkInwardContain = true;
                break;
            }
        }
    }
    if (!checkInwardContain) {
        customToast("Type and Size are not available",0);
        return;
    }

    var jsonObject = {};
    var inwardId = {};

    // Populate inwardId object with values
    inwardId["inwardMemoNumber"] = document.getElementById("inwardMemoNumber").value;
    inwardId["inwardItemSize"] = document.getElementById("inwardItemSize").value;
    inwardId["inwardItemType"] = document.getElementById("inwardItemType").value;

    // Populate jsonObject with other values
    jsonObject["inwardDate"] = document.getElementById("inwardDate").value;
    jsonObject["inwardDozen"] = document.getElementById("inwardDozen").value;
    jsonObject["inwardPiece"] = document.getElementById("inwardPiece").value;
    jsonObject["inwardId"] = inwardId;
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
        return response.json(); // Parse the response as JSON
    })
    .then(data => {
        if (data === 1) {
        customToast("New inward record added", 1);
        var newRow = document.createElement('tr');
        newRow.innerHTML = `
        <th scope="row">${document.querySelector(".inward-table-body").children.length + 1}</th>
        <td>${inwardMemoNumber}</td>
        <td>${inwardDate}</td>
        <td>${inwardItemSize}</td>
        <td>${inwardItemType}</td>
        <td>${inwardDozen}</td>
        <td>${inwardPiece}</td>
        `;
        document.querySelector(".inward-table-body").appendChild(newRow);
        } else {
        customToast("Item already present", 0);
        }
    })
    .catch(error => {
        console.error('Error:', error);
    });

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
        var val = document.getElementById("outwardPiece").value;
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

    if(!outwardBailNumber || !outwardDate || !outwardItemSize || !outwardItemType || !outwardDozen || !outwardPiece){
        customToast("Please enter valid data",0);
        return;
    }

    let checkOutwardContain = false;
    if (fullItemList) { // Ensure fullItemList is not null or undefined
        for (var i = 0; i < fullItemList.length; i++) {
            if (fullItemList[i].itemsId.itemSize == outwardItemSize && fullItemList[i].itemsId.itemType === outwardItemType) {
                checkOutwardContain = true;
                break;
            }
        }
    }
    if (!checkOutwardContain) {
        customToast("Type and Size are not available",0);
        return;
    }

    // Convert form data to JSON
    var jsonObject = {};
    var outwardId= {};

    // Populate inwardId object with values
    outwardId["outwardBailNumber"] = document.getElementById("outwardBailNumber").value;
    outwardId["outwardItemSize"] = document.getElementById("outwardItemSize").value;
    outwardId["outwardItemType"] = document.getElementById("outwardItemType").value;

    // Populate jsonObject with other values
    jsonObject["outwardDate"] = document.getElementById("outwardDate").value;
    jsonObject["outwardDozen"] = document.getElementById("outwardDozen").value;
    jsonObject["outwardPiece"] = document.getElementById("outwardPiece").value;
    jsonObject["outwardId"] = outwardId;

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
        return response.json(); // Parse the response as JSON
    })
    .then(data => {
        if (data === 1) {
            customToast("New outward record added",1);
            var newRow = document.createElement('tr');
            newRow.innerHTML = `
            <th scope="row">${document.querySelector(".outward-table-body").children.length + 1}</th>
            <td>${outwardBailNumber}</td>
            <td>${outwardDate}</td>
            <td>${outwardItemSize}</td>
            <td>${outwardItemType}</td>
            <td>${outwardDozen}</td>
            <td>${outwardPiece}</td>
            `;
            document.querySelector(".outward-table-body").appendChild(newRow);
        } else {
            customToast("Item already present", 0);
        }
    })
    .catch(error => {
        console.error('Error:', error);
    });

});
document.getElementById("addNewOutwardItem").addEventListener('click', function(){
    document.getElementById('outwardForm').reset();
    document.getElementById('outwardBailForm').reset();
    var tableBody = document.querySelector('.outward-table-body');
    tableBody.innerHTML = '';
});
////////////////////////////////OUTWARD END//////////////////////////////////////////

//////////////////////////////SEARCH//////////////////////////////////////////////////
const inwardRadio = document.getElementById('searchInward');
const outwardRadio = document.getElementById('searchOutward');
const searchAllItemsRadio = document.getElementById('searchAllItems');
const searchSalesRadio = document.getElementById('searchSales');
const searchForm = document.getElementById("searchForm");

inwardRadio.addEventListener('change', handleRadioChange);
outwardRadio.addEventListener('change', handleRadioChange);
searchAllItemsRadio.addEventListener('change', handleRadioChange);
searchSalesRadio.addEventListener('change', handleRadioChange);
searchForm.addEventListener('submit', handleFormSubmit);

function handleRadioChange(event) {
    const thElements = document.querySelectorAll('.tableSearchType th');
    let newHeaderNames;
    if (inwardRadio.checked) {
        newHeaderNames = ['Edit', '#', 'Memo Number', 'Date', 'Item Size', 'Item Type', 'Dozen', 'Piece'];
    } else if (outwardRadio.checked) {
        newHeaderNames = ['Edit', '#', 'Bail Number', 'Date', 'Item Size', 'Item Type', 'Dozen', 'Piece'];
    }
    else if(searchAllItemsRadio.checked){
        newHeaderNames = ['Edit', '#', 'Item Size', 'Item Type','','','',''];
    }
    else{
        newHeaderNames = ['Edit', '#', 'Item Size', 'Item Type','Dozen','Piece','',''];
    }
    thElements.forEach((th, index) => {
            th.innerHTML = newHeaderNames[index];
        });
    handleFormSubmit(event);
}

function handleFormSubmit(event) {
    event.preventDefault();
    let searchItemSize = document.getElementById("searchItemSize");
    let searchItemType = document.getElementById("searchItemType");
    let searchType = inwardRadio.checked ? "searchInwardItem" : outwardRadio.checked? "searchOutwardItem" : searchAllItemsRadio.checked? "searchAllItem" : "searchSalesItem";;

    var itemsIdObject = {
        "itemSize": searchItemSize.value,
        "itemType": searchItemType.value
    };
    jsonObject = {};
    jsonObject["itemsId"] = itemsIdObject;
    fetch(`/${searchType}`, {
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
    })
    .then(data => {
        if (data.length) {
            customToast("Record Found", 1);
        } else {
            customToast("No Record Found", 0);
        }
        if(searchSalesRadio.checked) displaySales(data);
        else displayItems(data);
    })
    .catch(error => {
        console.error('Error:', error);
    });
}

function displayItems(items) {
    const tableBody = document.querySelector(".search-table-body");
    tableBody.innerHTML = ""; // Clear existing rows
    var rowId = 1;
    items.forEach(item => {
        var newRow = document.createElement('tr');
        newRow.innerHTML = `<td><i class="edit-pension-icon fa-solid fa-pen-to-square" style="color: #5e62de;"><i class="ms-3 save-icon fa-solid fa-floppy-disk" style="color: #838486;"></i><i class="ms-3 delete-icon fa-solid fa-trash" style="color: #838486;"></i></td>
                          <th scope="row">${rowId++}</th>
                          <td>${item.inwardId?.inwardMemoNumber || item.outwardId?.outwardBailNumber || item.itemsId?.itemSize || item.salesId?.salesItemSize}</td>
                          <td>${item.inwardDate || item.outwardDate || item.itemsId?.itemType || item.salesId?.salesItemType}</td>
                          <td>${item.inwardId?.inwardItemSize || item.outwardId?.outwardItemSize || item.salesDozen || ""}</td>
                          <td>${item.inwardId?.inwardItemType || item.outwardId?.outwardItemType || item.salesPiece ||""}</td>
                          <td>${item.inwardDozen || item.outwardDozen || ""}</td>
                          <td>${item.inwardPiece || item.outwardPiece || ""}</td>`;
        if(item.salesId){
            tableBody.appendChild(newRow);
            return;
        }
        const editPensionIcon = newRow.querySelector('.edit-pension-icon');
        // Add click event listener to the edit pension icon
        editPensionIcon.addEventListener('click', () => {
            let jsonObjectOld = JSON.parse(JSON.stringify(item));
            newRow.innerHTML = `<td><i class="edit-pension-icon fa-solid fa-pen-to-square" style="color: #5e62de;"><i class="ms-3 save-icon fa-solid fa-floppy-disk" style="color: #10b141;"></i><i class="ms-3 delete-icon fa-solid fa-trash" style="color: #f32020;"></i></td>
                               <th scope="row">${rowId}</th>
                               <td><input class="edit-input form-control" type="text" value="${item.inwardId?.inwardMemoNumber || item.outwardId?.outwardBailNumber || item.itemsId?.itemSize}"></td>
                               <td><input class="edit-input form-control" type="text" value="${item.inwardDate || item.outwardDate || item.itemsId?.itemType}"></td>
                               <td><input class="edit-input form-control" type="text" value="${item.inwardId?.inwardItemSize || item.outwardId?.outwardItemSize || ""}"></td>
                               <td><input class="edit-input form-control" type="text" value="${item.inwardId?.inwardItemType || item.outwardId?.outwardItemType || ""}"></td>
                               <td><input class="edit-input form-control" type="text" id="edit-input-dozen" value="${item.inwardDozen || item.outwardDozen || ""}"></td>
                               <td><input class="edit-input form-control" type="text" id="edit-input-piece" value="${item.inwardPiece || item.outwardPiece || ""}"></td>`;
            if(item.itemsId)
            newRow.innerHTML = `<td><i class="edit-pension-icon fa-solid fa-pen-to-square" style="color: #5e62de;"><i class="ms-3 save-icon fa-solid fa-floppy-disk" style="color: #10b141;"></i><i class="ms-3 delete-icon fa-solid fa-trash" style="color: #f32020;"></i></td>
                                           <th scope="row">${rowId}</th>
                                           <td><input class="edit-input form-control" type="text" value="${item.itemsId?.itemSize}"></td>
                                           <td><input class="edit-input form-control" type="text" value="${item.itemsId?.itemType}"></td>
                                           <td></td>
                                           <td></td>
                                           <td></td>
                                           <td></td>`;

            const editInputs = newRow.querySelectorAll('.edit-input');
            const saveIcon = newRow.querySelector('.save-icon');
            const deleteIcon = newRow.querySelector('.delete-icon');
            editPensionIcon.style.display = 'none';
            saveIcon.style.display = 'inline';
            deleteIcon.addEventListener('click',() => {
                if(item.inwardId) deleteItem('/deleteInward',item);
                else if(item.outwardId) deleteItem('/deleteOutward',item);
                else if(item.itemsId) deleteItem('/deleteItem',item);
                newRow.remove();
            });

            saveIcon.addEventListener('click', () => {
                if(item.itemsId){
                    if (
                        editInputs[0].value === null || editInputs[0].value.trim() === '' ||
                        editInputs[1].value === null || editInputs[1].value.trim() === ''
                    ) {
                        customToast("Please enter all values",0);
                        return;
                    }
                }else{
                    if (
                        editInputs[0].value === null || editInputs[0].value.trim() === '' ||
                        editInputs[1].value === null || editInputs[1].value.trim() === '' ||
                        editInputs[2].value === null || editInputs[2].value.trim() === '' ||
                        editInputs[3].value === null || editInputs[3].value.trim() === '' ||
                        editInputs[4].value === null || editInputs[4].value.trim() === '' ||
                        editInputs[5].value === null || editInputs[5].value.trim() === ''
                    ) {
                        customToast("Please enter all values",0);
                        return;
                    }
                    let checkOutwardContain = false;
                    if (fullItemList) { // Ensure fullItemList is not null or undefined
                        for (var i = 0; i < fullItemList.length; i++) {
                            if (fullItemList[i].itemsId.itemSize == editInputs[2].value && fullItemList[i].itemsId.itemType === editInputs[3].value) {
                                checkOutwardContain = true;
                                break;
                            }
                        }
                    }
                    if (!checkOutwardContain) {
                        customToast("Type and Size are not available",0);
                        return;
                    }
                }
                if (item.inwardId) {
                    item.inwardId.inwardMemoNumber = editInputs[0].value;
                    item.inwardId.inwardItemSize = editInputs[2].value;
                    item.inwardId.inwardItemType = editInputs[3].value;
                    item.inwardDate = editInputs[1].value;
                    item.inwardDozen = editInputs[4].value;
                    item.inwardPiece = editInputs[5].value;
                    updateItem('/deleteInward','/addInward',jsonObjectOld,item);
                }
                else if (item.outwardId) {
                    item.outwardId.outwardBailNumber = editInputs[0].value;
                    item.outwardId.outwardItemSize = editInputs[2].value;
                    item.outwardId.outwardItemType = editInputs[3].value;
                    item.outwardDate = editInputs[1].value;
                    item.outwardDozen = editInputs[4].value;
                    item.outwardPiece = editInputs[5].value;
                    updateItem('/deleteOutward','/addOutward',jsonObjectOld,item);
                }
                else if (item.itemsId) {
                    item.itemsId.itemSize = editInputs[0].value;
                    item.itemsId.itemType = editInputs[1].value;
                    updateItem('/deleteItem','/addItem',jsonObjectOld,item);
                }
                newRow.innerHTML = `<td><i class="edit-pension-icon fas fa-pencil-alt"></i><i class="ms-3 save-icon fas fa-save"></i><i class="ms-3 delete-icon fas fa-trash-alt"></i></td>
                               <th scope="row">${rowId}</th>
                               <td>${item.inwardId?.inwardMemoNumber || item.outwardId?.outwardBailNumber || item.itemsId?.itemSize}</td>
                               <td>${item.inwardDate || item.outwardDate || item.itemsId?.itemType}</td>
                               <td>${item.inwardId?.inwardItemSize || item.outwardId?.outwardItemSize || ""}</td>
                               <td>${item.inwardId?.inwardItemType || item.outwardId?.outwardItemType || ""}</td>
                               <td>${item.inwardDozen || item.outwardDozen || ""}</td>
                               <td>${item.inwardPiece || item.outwardPiece || ""}</td>`;
                displayItems(items);
            });
            if(item.inwardId || item.outwardId){
                document.getElementById("edit-input-dozen").addEventListener("input", function() {
                        var val = document.getElementById("edit-input-dozen").value;
                        if(!val) return;
                        var dozenValue = parseInt(val);
                        var pieceValue = dozenValue * 12;
                        document.getElementById("edit-input-piece").value = pieceValue;
                });
                document.getElementById("edit-input-piece").addEventListener("input", function() {
                        var val = document.getElementById("edit-input-piece").value;
                        if(!val) return;
                        var pieceValue = parseInt(val);
                        var dozenValue = Math.round(pieceValue/12 * 10 ** 2) / 10 ** 2;
                        document.getElementById("edit-input-dozen").value = dozenValue;
                });
            }
        });
        tableBody.appendChild(newRow);
    });
}

function displaySales(items) {
    const tableBody = document.querySelector(".search-table-body");
    tableBody.innerHTML = ""; // Clear existing rows
    var rowId = 1;
    items.forEach(item => {
        const isNegative = item.salesDozen < 0 || item.salesPiece < 0;
        var newRow = document.createElement('tr');
        newRow.innerHTML = `<td><i class="edit-pension-icon fa-solid fa-pen-to-square" style="color: #5e62de;"><i class="ms-3 save-icon fa-solid fa-floppy-disk" style="color: #838486;"></i><i class="ms-3 delete-icon fa-solid fa-trash" style="color: #838486;"></i></td>
                          <th scope="row">${rowId++}</th>
                          <td>${item.salesId?.salesItemSize}</td>
                          <td>${item.salesId?.salesItemType}</td>
                          <td>${item.salesDozen}</td>
                          <td>${item.salesPiece}</td>
                          <td></td>
                          <td></td>`;
        if (isNegative) {
            newRow.classList.add('negative-row');
        }
        tableBody.appendChild(newRow);
    })
}


function updateItem(deleteUrl,addUrl,jsonObjectOld, jsonObject) {
    console.log('update old ',jsonObjectOld);
    console.log('update new',jsonObject);
    fetch(deleteUrl,{
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(jsonObjectOld)
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return response.json();
    })
    .then(data =>{
        console.log("Delete called ", data);
        if(data == 1 ) {
            addItem(addUrl,jsonObject);
        }
    })
    .catch(error => {
        console.error('Error:', error);
    });
}

function addItem(addUrl,jsonObject) {
    fetch(addUrl, {
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
    })
    .then(data =>{
        if(data == 1) {
            customToast("Updated item", 1);
        }
        else{
            customToast("Updated item", 1);
        }
    })
    .catch(error => {
        console.error('Error:', error);
    });
}
function deleteItem(deleteUrl,jsonObject) {
    fetch(deleteUrl, {
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
        customToast("Item Deleted", 1);
        return response.json();
    })
    .then(data =>{
        if(data == 1) {
            console.log("Deleted successful");
        }
    })
    .catch(error => {
        console.error('Error:', error);
    });
}
//////////////////////////////SEARCH END///////////////////////////////////////////////

////////////CUSTOM TOAST///////////////////
function customToast(msg,status){
    const toastBootstrap = bootstrap.Toast.getOrCreateInstance(toast);
    toastText = document.getElementById("toastMessage");
    toastText.innerText = msg;
    if(status===1){
        toastText.classList.remove("text-bg-danger");
        toastText.classList.add("text-bg-success");
    }else{
        toastText.classList.add("text-bg-danger");
        toastText.classList.remove("text-bg-success");
    }
    toastBootstrap.show();
}