#version 140

in vec2 texCoords1;
in vec2 texCoords2;
in float blend;

out vec4 out_Color;

uniform sampler2D particleTexture;

void main(void){
	vec4 color1 = texture(particleTexture, texCoords1);
	vec4 color2 = texture(particleTexture, texCoords2);

	out_Color = mix(color1, color2, blend);
}
