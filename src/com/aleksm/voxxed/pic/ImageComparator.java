package com.aleksm.voxxed.pic;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class ImageComparator {

	public static final String FIRST_IMAGE = "first.png";
	public static final String SECOND_IMAGE = "second.png";
	public static final String OUTPUT_IMAGE = "diff.png";
	
	private String mFirstImage;
	private String mSecondImage;
	private String mOutputImage;
	
	public ImageComparator(String firstImage, String secondImage, String outputImage){
		this.mFirstImage = firstImage;
		this.mSecondImage = secondImage;
		this.mOutputImage = outputImage;
	}
	
	public static void main(String[] args) {
		
		try{	
			if (args.length != 0 && args.length !=3){
				System.out.println("You must have 3 parameters or none if you want to see the result :)");
				return;
			}
	
			ImageComparator comparator;
			
			if (args.length == 0){
				comparator = new ImageComparator(FIRST_IMAGE, SECOND_IMAGE, OUTPUT_IMAGE);
			}else{
				comparator = new ImageComparator(args[0], args[1], args[2]);
			}
					
			BufferedImage firstImageFromFile, secondImageFromFile, outputImageToFile;

		
			File firstFile = new File(comparator.mFirstImage);
			File secondFile = new File(comparator.mSecondImage);
			
			firstImageFromFile = comparator.readPngFile(firstFile);
			secondImageFromFile = comparator.readPngFile(secondFile);
			
			if (firstImageFromFile.getHeight() != secondImageFromFile.getHeight() ||
					firstImageFromFile.getWidth() != secondImageFromFile.getWidth()){
				System.out.println("Only 2 images of the same size can be compared for now!");
				return;
			}
			
			File outputFile = new File(comparator.mOutputImage);
			outputImageToFile = comparator.compareFiles(firstImageFromFile, secondImageFromFile);
			
			comparator.writeToPngFile(outputFile, outputImageToFile);
			
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}

	public BufferedImage readPngFile(File file) throws Exception{
		BufferedImage bufferedImage = ImageIO.read(file);
		return bufferedImage;
	}

	public void writeToPngFile(File file, BufferedImage bufferedImage) throws Exception{
		ImageIO.write(bufferedImage, "png", file);
	}
	
	public BufferedImage compareFiles (BufferedImage image1, BufferedImage image2){
		int width = image1.getWidth();
		int height = image1.getHeight();
		
		BufferedImage diffImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);	
		
		int whiteRGB = new Color(255, 255, 255).getRGB();
		int blackRGB = new Color(0, 0, 0).getRGB();
		
		for(int i=0;i<height;i++){
			for (int j=0;j<width;j++){
				if (image1.getRGB(j,i) == image2.getRGB(j,i))
					diffImage.setRGB(j,i,whiteRGB);
				else
					diffImage.setRGB(j, i, blackRGB);
			}
		}
		return diffImage;
	}
	
}
