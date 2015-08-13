#version 150

in vec2 vertexPosition;
in vec3 vertexColor;

uniform mat4 projection;
uniform mat4 camera;
uniform mat4 model;

out vec2 fragmentPosition;
out vec3 fragmentColor;

void main(){
	fragmentPosition = vertexPosition;
	fragmentColor = vertexColor;
	gl_Position = projection * camera * model * vec4(vertexPosition, 0.0, 1.0);
}
