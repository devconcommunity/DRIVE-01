(function(){
	angular.module('ev3', [])
		.controller('movementController', function($scope, $http){

			//function to send data to the server
			$scope.movement = function(letter){

				//initialize variables
				var move = "";
				var time = $scope.time;
				//time will be 3 seconds by default if the provided value is less than 0 or no value provided
				if (time == null || time < 0){
					time = 3.0;
				}

				//switch statement to differentiate between button pressed
				//calls up the function in connect.js while passing the parameters
				switch(letter){
					case "A" :
						move = "forward";
						window.sendinSocket(move, time);
						break;
					case "B" :
						move = "backward";
						window.sendinSocket(move, time);
						break;
					case "C" :
						move = "left";
						window.sendinSocket(move, time);
						break;
					case "D" :
						move = "right";
						window.sendinSocket(move, time);
						break;
				}
				// var data2send = {
				// 	movement : move
				// };

				// $http.post('/api/movement',data2send).success(function(response){
				// 		console.log("Success");
				// }).error(function(err){
				// 	console.log(err);
				// })

			}

		});
}());