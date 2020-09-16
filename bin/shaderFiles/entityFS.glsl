#version 400 core

const int MAX_LIGHTS = 10;

in vec2 pass_texCoords;
in vec3 surfaceNormal;
in vec3 toLightVector[MAX_LIGHTS];
in vec3 toCameraVector;
in float visibility;

out vec4 out_Color;

uniform sampler2D texSampler;
uniform vec3 lightColor[MAX_LIGHTS];
uniform vec3 attenuation[MAX_LIGHTS];
uniform float shineDamper;
uniform float reflectivity;
uniform vec3 skyColor;
uniform int numOfLights;

void main(void) {

	vec3 unitNormal = normalize(surfaceNormal);
	vec3 unitVectorToCamera = normalize(toCameraVector);
	
	vec3 totalDiffuse = vec3(0.0);
	vec3 totalSpecular = vec3(0.0);
	
	for (int i = 0; i < numOfLights; i++) {
		float distance = length(toLightVector[i]);
		float attFactor = attenuation[i].x + (attenuation[i].y * distance) + (attenuation[i].z * distance * distance);
		
		vec3 unitLightVector = normalize(toLightVector[i]);
		float nDotl = dot(unitNormal, unitLightVector);
		float brightness = max(nDotl, 0.0);
		vec3 lightDirection = -unitLightVector;
		vec3 reflectedLightDirection = reflect(lightDirection, unitNormal);
		
		float specularFactor = dot(reflectedLightDirection, unitVectorToCamera);
		specularFactor = max(specularFactor, 0.0);
		float damperFactor = pow(specularFactor, shineDamper);
		totalDiffuse = totalDiffuse + (brightness * lightColor[i]) / attFactor;
		totalSpecular = totalSpecular + (damperFactor * reflectivity * lightColor[i]) / attFactor;
	}
	
	totalDiffuse = max(totalDiffuse, 0.2);
	
	vec4 texColor = texture(texSampler, pass_texCoords);
	if(texColor.a<0.5){
		discard;
	}

	out_Color = vec4(totalDiffuse, 1.0) * texColor + vec4(totalSpecular, 1.0);
	out_Color = mix(vec4(skyColor,1.0), out_Color, visibility);
}