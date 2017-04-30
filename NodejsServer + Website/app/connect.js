
//establishing socket connection to the server
try{
	var socket = io.connect();
}catch(e){
	console.log("Error connectig to server");
}

//function to send the data from front-end to back-end
function sendinSocket(string, time){
	
	//JSON formatting
	var data = {
		test : string,
		time : time
	};

	//transferring data through socket connection
	socket.emit('direction', data);
}


// socket.on('testq', function(data){
// 	console.log(data);
// });