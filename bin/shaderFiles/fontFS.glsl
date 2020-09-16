#version 330

in vec2 pass_texCoords;

out vec4 out_Color;

uniform vec3 color;
uniform sampler2D fontAtlas;

uniform vec3 outlineColor;

uniform vec2 offset;

uniform float width;
uniform float edge;

uniform float borderWidth = 0.0;
uniform float borderEdge = 0.5;

void main(void){

	float distance = 1 - texture(fontAtlas, pass_texCoords).a;
	float alpha = 1 - smoothstep(width, width + edge, distance);

	float distance1 = 1 - texture(fontAtlas, pass_texCoords + offset).a;
	float outlineAlpha = 1 - smoothstep(borderWidth, borderWidth + borderEdge, distance1);

	float overallAlpha = alpha + (1 - alpha) * outlineAlpha;
	vec3 overallColor = mix(outlineColor, color, alpha / overallAlpha);

	out_Color = vec4(overallColor, overallAlpha);
}
