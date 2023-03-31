function delete_task(task_id){
    let url = "/" +task_id;
    $.ajax({
        url:url,
        type: "DELETE",
        success: function (){
            window.location.reload();
        }
    })
}

function edit_task(task_id){
    let identifier_delete = "delete_" +task_id;

}