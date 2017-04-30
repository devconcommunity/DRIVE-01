//Load the dependencies
var express 	= require('express'), //express framework 
	app			= express(),
	// mongoose	= require('mongoose'),
	http		= require('http').Server(app), //for http connection
	client		= require('socket.io').listen(http), //for socket connection
	bodyParser	= require('body-parser');// to read json data from client

//set the port here
var port = 8000;

//starts a server on the specified port 
http.listen(port, function(){
	console.log("Listening at port : " + port);
});

// mongoose.connect('mongodb://localhost:27017/EV3');

//Tells the nodejs server to use these directories and dependency
app.use(bodyParser.json());
app.use('/node_modules', express.static(__dirname + "/node_modules"));
app.use('/app', express.static(__dirname + "/app"));


//wait for connection from a client
client.on('connection', function(socket){
	console.log('connected');

	//socket call from client 
	socket.on("direction", function(data){
		//function call
		sending(socket, data.test, data.time);
		console.log(data); //check if the data is received from the client
	});

	//socket call from android app
	socket.on('foo', function(data){
		console.log(data); //checking connection
	});

});

//serve the user with the index.html file
app.get('/', function(req,res){
	res.sendFile('index.html', {root:__dirname}); 
});


// app.post('/api/movement', function(req,res){

// 	var data = req.body.movement;
// 	//console.log(data);

// 	res.json("Success");

// });

//function to send the data from web application to all connected clients 
var sending = function(socket, direction, time1){

	//JSON formatting
	data = {
		test : direction,
		time: time1
	};
	
	//using "client" instead of "socket" to emit to all connected clients
	client.emit('incoming', data);
}


