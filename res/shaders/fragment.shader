#version 150

in vec2 fragmentPosition;
in vec3 fragmentColor;

out vec4 finalColor;

void main(){
	finalColor = vec4(fragmentColor, max(abs(fragmentPosition.x), abs(fragmentPosition.y)));
}