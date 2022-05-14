$(document).ready(function()
{
if ($("#alertSuccess").text().trim() == "")
 {
 $("#alertSuccess").hide();
 }
 $("#alertError").hide();
});

//SAVE ============================================
$(document).on("click", "#btnSave", function(event)
{
// Clear alerts---------------------
 $("#alertSuccess").text("");
 $("#alertSuccess").hide();
 $("#alertError").text("");
 $("#alertError").hide();
// Form validation-------------------
var status = validateUserForm();
if (status != true)
 {
 $("#alertError").text(status);
 $("#alertError").show();
 return;
 }

var type = ($("#idpayment").val() == "") ? "POST" : "PUT";


$.ajax(
		{
		 url : "PaymentsAPI",
		 type : type,
		 data : $("#formUser").serialize(),
		 dataType : "text",
		 complete : function(response, status)
		 {
		 onUserSaveComplete(response.responseText, status);
		 }
		});

});

function onUserSaveComplete(response, status)
{
if (status == "success")
 {
	var resultSet = JSON.parse(response);
	if (resultSet.status.trim() == "success")
	{
		$("#alertSuccess").text("Successfully saved.");
		$("#alertSuccess").show();
		
		$("#divUserGrid").html(resultSet.data);
	} else if (resultSet.status.trim() == "error")
	{
		$("#alertError").text(resultSet.data);
		$("#alertError").show();
	}
 	} else if (status == "error")
 	{
 		$("#alertError").text("Error while saving.");
 		$("#alertError").show();
 	} else
 	{
 		$("#alertError").text("Unknown error while saving..");
 		$("#alertError").show();
 	}
		$("#idcomplain").val("");
		$("#formUser")[0].reset();
}

//UPDATE==========================================
$(document).on("click", ".btnUpdate", function(event)
{
 $("#idpayment").val($(this).closest("tr").find('#hididUpdate').val());
 $("#accountno").val($(this).closest("tr").find('td:eq(0)').text());
 $("#nic").val($(this).closest("tr").find('td:eq(1)').text());
 $("#amount").val($(this).closest("tr").find('td:eq(2)').text());
 $("#date").val($(this).closest("tr").find('td:eq(3)').text());
});


$(document).on("click", ".btnRemove", function(event)
		{
		 $.ajax(
		 {
		 url : "PaymentsAPI",
		 type : "DELETE",
		 data : "idpayment=" + $(this).data("idpayment"),
		 dataType : "text",
		 complete : function(response, status)
		 {
		 onUserDeleteComplete(response.responseText, status);
		 }
		 });
		});

function onUserDeleteComplete(response, status)
{
if (status == "success")
 {
 var resultSet = JSON.parse(response);
 if (resultSet.status.trim() == "success")
 {
 $("#alertSuccess").text("Successfully deleted.");
 $("#alertSuccess").show();
 $("#divUserGrid").html(resultSet.data);
 } else if (resultSet.status.trim() == "error")
 {
 $("#alertError").text(resultSet.data);
 $("#alertError").show();
 }
 } else if (status == "error")
 {
 $("#alertError").text("Error while deleting.");
 $("#alertError").show();
 } else
 {
 $("#alertError").text("Unknown error while deleting..");
 $("#alertError").show();
 }
}
//CLIENTMODEL=========================================================================
function validateUserForm()
{
	
//accountno
if ($("#accountno").val().trim() == "")
{
return "Insert accountno.";
}


//nic
if ($("#nic").val().trim() == "")
{
return "Insert nic.";
}


//amount
if ($("#amount").val().trim() == "")
{
return "Insert amount.";
} 

//date
if ($("#date").val().trim() == "")
{
return "Insert date.";
}
return true;
}



