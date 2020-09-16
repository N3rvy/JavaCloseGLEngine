#version 400 core

const int MAX_LIGHTS = 10;

in vec3 pos;
in vec2 texCoords;
in vec3 normal;

out vec2 pass_texCoords;
out vec3 surfaceNormal;
out vec3 toLightVector[MAX_LIGHTS];
out vec3 toCameraVector;
out float visibility;

uniform mat4 transMatrix;
uniform mat4 projMatrix;
uniform mat4 viewMatrix;
uniform vec3 lightPosition[MAX_LIGHTS];

uniform float useFakeLighting;

uniform float numberOfRows;
uniform vec2 offset;

uniform int numOfLights = 10;

const float density = 0.0035;
const float gradient = 5.0;

void main(void) {
	
	vec4 worldPosition = transMatrix * vec4(pos, 1.0);
	vec4 positionRelativeToCamera = viewMatrix * worldPosition;
	gl_Position = projMatrix * positionRelativeToCamera;
	pass_texCoords = (texCoords / numberOfRows) + offset;
	
	vec3 actualNormal = normal;
	if(useFakeLighting > 0.5) {
		actualNormal = vec3(0.0,1.0,0.0);
	}
	
	surfaceNormal = (transMatrix * vec4(actualNormal, 0.0)).xyz;
	for (int i = 0; i < numOfLights; i++) {
		toLightVector[i] = lightPosition[i] - worldPosition.xyz;
	}
	toCameraVector = (inverse(viewMatrix) * vec4(0.0,0.0,0.0,1.0)).xyz - worldPosition.xyz;
	
	float distance = length(positionRelativeToCamera.xyz);
	visibility = exp(-pow((distance * density), gradient));
	visibility = clamp(visibility, 0.0, 1.0);
}
