################################
#### Panono Code Challenge  ####
################################
Develop a restful API that generates statistics data of panorama uploads. The main
use case is to calculate real time statistics of panorama uploads to our stitching
service in the last 60 seconds. Users can batch upload panoramas. To accomplish
this, two API endpoints need to be implemented. One is responsible for the input
of the data, the other to retrieve the relevant statistics.

################
#### Specs  ####
################
POST /upload
	Every time a users uploads a batch of panoramas, this endpoint will be called.
	Body
	{
		"count": 3,
		"timestamp": 1512243683623,
		"links": [
    		{
      		"href": "string",
      		"rel": "string"
    		}
		]
	}
	count - Number of uploaded panoramas
	timestamp - Epoch time of upload in UTC
	Returns
	Empty body with either 201 or 204
	201 - in case of success
	204 - timestamp if older than 60 seconds
GET /statistics
	This is the main endpoint of this tasks. Since the API has to be scalable, it is
	important that it execute in constant time and memory O(1). The endpoint returns
	the statistics of uploads in the last 60 seconds.
	Returns
	{
		"sum": 3,
		"avg": 1.0,
		"max": 2,
		"min": 1,
		"count: 2
	}
	sum - Total amount of uploaded panoramas
	avg - Average amount of uploaded panoramas per batch
	max - Maximum of uploaded panoramas per batch
	min - Minimum amount of panoramas per batch

################################
### Requirements  ###
################################
The API is required to run in constant time and space to be scalable. Other
requirements that are obvious, but also listed here explicitly:
	The API has to be threadsafe
	The API has to function properly over a longer period of time
	The project should be easily buildable (gradle or maven are prefered)
	The API should be able to deal with time discrepancy, since timing issues can
always occur.
	Do not use any database including only-memory databases.
	The endpoints have to execute in constant time and memory (O(1))
	API has to be fully tested including unit tests and end to end tests.

################################
### Running the application  ###
################################
This is an Springboot application - REST microservice.
 - To build the application do: mvn clean install
 - To run the application do: mvn spring-boot:run
 - The app has been set with swagger, to try the app just go to: http://localhost:8081/panono/swagger-ui.html
 	JSON examples can be seen in swagger.
 
 
**********************************************************************************************************************
**  NOTE: THE SOLUTION TO THIS CHALLENGE WAS @:																		**
** 	https://github.com/nsmolenskii/n26-metrics/tree/master/src/main/java/de/nsmolenskii/experiments/n26/utils		**
**********************************************************************************************************************

  