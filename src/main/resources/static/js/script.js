/**
 * 
 */

 console.log("This is script file");
 const toggleSidebar = () => {
	 if($('.sidebar').is(":visible")){
		 $(".sidebar").css("display","none");
		 $(".content").css("margin-left","0%");
	 }else{
		  $(".sidebar").css("display","block");
		 $(".content").css("margin-left","20%");
	 }
 };

 
/**
 * 
 

console.log("This is script file");

const toggleSidebar = () => {
    if ($('.sidebar').is(":visible")) {
        $(".sidebar").css("display", "none");
        $(".content").css("margin-left", "0%");
    } else {
        $(".sidebar").css("display", "block");
        $(".content").css("margin-left", "20%");
    }
};
*/

const search = () => {
    console.log("Searching");
    let query = $("#search_input").val();
    console.log("Query:", query);  // Add this line to check the value of query
    if (query ==="") {
        console.log("Empty query");
        // Handle the case where the query is empty.
        $(".search-result").hide();
    } else {
        console.log(query);
        // Sending a request to the server
        let url = `http://localhost:8080/search/${query}`;
        fetch(url)
            .then((response) => {
                return response.json();
            })
            .then((data) => {
                console.log(data);
                let text = `<div class='list-group'>`;
                data.forEach((contact) => {
                    text = text+`<a href='/user/${contact.C_Id}/contact' class='list-group-item list-group-action'>${contact.name}</a>`;
                });
                text += `</div>`;
                
                $(".search-result").html(text);
                if(data.length>0){
					$(".search-result").show();
				}
            });
        
    }
};



