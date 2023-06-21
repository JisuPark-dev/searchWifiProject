function deleteRow(id) {
    fetch(`/delete?id=${id}`, { method: 'POST' })
        .then(response => {
            if (!response.ok) throw new Error("Deletion failed");
            return response.text();
        })
        .then(data => {
            alert("Row with id " + id + " was successfully deleted.");
            location.reload();
        })
        .catch(error => {
            console.error("Error:", error);
            alert("Deletion failed.");
            }
        );
}

