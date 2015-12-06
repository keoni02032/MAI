package objects;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import main.Shader;
import math.Matrix;

public class Terrain extends Entity {
	public Terrain() {
		float topLimit = 16.0f;
		int texSize = 64;
		int minColor = 255;
		int maxColor = 0;

		points = new ArrayList<>();
		texCoords = new ArrayList<>();
		indices = new ArrayList<>();

		try {
			BufferedImage bi = ImageIO.read(getClass().getResource("../resources/textures/terrain_hm3.png"));

			width = bi.getWidth();
			height = bi.getHeight();

			for (int i = 0; i < height; ++i) {
				for (int j = 0; j < width; ++j) {
					int color = bi.getRGB(j, i) & 255;

					if (color < minColor) {
						minColor = color;
					}

					if (color > maxColor) {
						maxColor = color;
					}
				}
			}

			float colorDiff = maxColor - minColor + 1.0f;

			for (int i = 0; i < height; ++i) {
				for (int j = 0; j < width; ++j) {
					int color = (bi.getRGB(j, i) & 255) - minColor;
					float x = (float)j;
					float y = topLimit * color / colorDiff;
					float z = (float)i;
					float texCoordX = (float)j / texSize;
					float texCoordY = 1.0f - (float)i / texSize;

					points.add(x);
					points.add(y);
					points.add(z);

					texCoords.add(texCoordX);
					texCoords.add(texCoordY);
				}
			}

			for (int i = 0; i < height - 1; ++i) {
				for (int j = 0; j < width - 1; ++j) {
					int offset = i * width + j;

					indices.add(offset);
					indices.add(offset + width + 1);
					indices.add(offset + width);
					indices.add(offset);
					indices.add(offset + 1);
					indices.add(offset + width + 1);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		try
		{
			texture = TextureLoader.getTexture("png", getClass().getResource("../resources/textures/terrain5.png").openStream());
		} catch (IOException e) {
			e.printStackTrace();
		}

		shader = new Shader("src/resources/shaders/terrain");
	}

	@Override
	public void compile() {
		GL11.glNewList(dispList, GL11.GL_COMPILE);
		GL11.glBegin(GL11.GL_TRIANGLES);
		GL11.glColor3f(1.0f, 1.0f, 1.0f);

		for (int i = 0; i < indices.size(); i += 3) {
			for (int j = 0; j < 3; ++j) {
				GL11.glTexCoord2f(texCoords.get(2 * indices.get(i + j)),
						texCoords.get(2 * indices.get(i + j) + 1));

				GL11.glVertex3f(
						points.get(3 * indices.get(i + j)),
						points.get(3 * indices.get(i + j) + 1),
						points.get(3 * indices.get(i + j) + 2));
			}
		}

		GL11.glEnd();
		GL11.glEndList();
	}

	@Override
	public void draw(Matrix projMat, Matrix viewMat) {
		texture.bind();
		shader.bind();
		shader.setUniform1("u_sampler", 0);
		shader.setUniformMatrix4("u_mvp", projMat.mul(viewMat).toBuffer());

		GL11.glCallList(dispList);

		shader.unbind();
	}

	@Override
	public void delete() {
		shader.delete();
		texture.release();

		super.delete();
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	private int width;
	private int height;
	private ArrayList<Float> points;
	private ArrayList<Float> texCoords;
	private ArrayList<Integer> indices;
	private Texture texture;
	private Shader shader;
}
