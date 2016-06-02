package com.youku.mobile.utils;

import java.io.IOException;
import java.math.BigDecimal;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.InputFormat;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.OutputFormat;
import org.apache.hadoop.mapreduce.Reducer;

public class UtilFuns {

	private static class UtilFunsHolder {
		private static final UtilFuns INSTANCE = new UtilFuns();
	}

	private UtilFuns() {
	}

	public static final UtilFuns getInstance() {
		return UtilFunsHolder.INSTANCE;
	}

	/**
	 * 设置driver，包含mapper和reducer的方式
	 * 
	 * @param inputPath
	 * @param outputPath
	 * @param inputFormat 输入文件格式
	 * @param mapper
	 * @param mapperKey mapper的输出key类型
	 * @param mapperValue mapper的输出value类型
	 * @param reducer 
	 * @param reducerKey reducer的输出key类型 
	 * @param reducerValue reducer的输出value类型
	 * @param outputFormat 输出文件格式
	 * @param conf
	 * @return
	 * @throws IOException
	 */
	public static Job prepareJob(Path inputPath, Path outputPath, Class<? extends InputFormat> inputFormat,
			Class<? extends Mapper> mapper, Class<? extends Writable> mapperKey, Class<? extends Writable> mapperValue,
			Class<? extends Reducer> reducer, Class<? extends Writable> reducerKey,
			Class<? extends Writable> reducerValue, Class<? extends OutputFormat> outputFormat, Configuration conf)
					throws IOException {

		@SuppressWarnings("deprecation")
		Job job = new Job(conf);
		Configuration jobConf = job.getConfiguration();

		if (reducer.equals(Reducer.class)) {
			if (mapper.equals(Mapper.class)) {
				throw new IllegalStateException("Can't figure out the user class jar file from mapper/reducer");
			}
			job.setJarByClass(mapper);
		} else {
			job.setJarByClass(reducer);
		}

		job.setInputFormatClass(inputFormat);
		jobConf.set("mapreduce.input.fileinputformat.inputdir", inputPath.toString());

		job.setMapperClass(mapper);
		if (mapperKey != null) {
			job.setMapOutputKeyClass(mapperKey);
		}
		if (mapperValue != null) {
			job.setMapOutputValueClass(mapperValue);
		}

		jobConf.setBoolean("mapreduce.map.output.compress", true);

		job.setReducerClass(reducer);
		job.setOutputKeyClass(reducerKey);
		job.setOutputValueClass(reducerValue);

		job.setOutputFormatClass(outputFormat);
		jobConf.set("mapreduce.output.fileoutputformat.outputdir", outputPath.toString());

		return job;
	}

	/**
	 * 设置driver ， 只包含mapper
	 * @param inputPath
	 * @param outputPath
	 * @param inputFormat
	 * @param mapper
	 * @param mapperKey
	 * @param mapperValue
	 * @param outputFormat
	 * @param conf
	 * @return
	 * @throws IOException
	 */
	public static Job prepareJob(Path inputPath, Path outputPath, Class<? extends InputFormat> inputFormat,
			Class<? extends Mapper> mapper, Class<? extends Writable> mapperKey, Class<? extends Writable> mapperValue,
			Class<? extends OutputFormat> outputFormat, Configuration conf) throws IOException {

		@SuppressWarnings("deprecation")
		Job job = new Job(conf);
		Configuration jobConf = job.getConfiguration();

		if (mapper.equals(Mapper.class)) {
			throw new IllegalStateException("Can't figure out the user class jar file from mapper/reducer");
		}
		job.setJarByClass(mapper);

		job.setInputFormatClass(inputFormat);
		jobConf.set("mapreduce.input.fileinputformat.inputdir", inputPath.toString());

		job.setMapperClass(mapper);
		job.setMapOutputKeyClass(mapperKey);
		job.setMapOutputValueClass(mapperValue);
		job.setOutputKeyClass(mapperKey);
		job.setOutputValueClass(mapperValue);
		jobConf.setBoolean("mapreduce.map.output.compress", true);
		job.setNumReduceTasks(0);

		job.setOutputFormatClass(outputFormat);
		jobConf.set("mapreduce.output.fileoutputformat.outputdir", outputPath.toString());

		return job;
	}
	
	/**
	 * mv 数据 
	 * @param conf
	 * @param fromPath 输入路径
	 * @param toPath 输出路径
	 * @param srcPref 输入文件前缀
	 * @param desPref 输出文件前缀
	 * @throws IOException
	 */
	public static void mvData(Configuration conf, Path fromPath, Path toPath, String srcPref, String desPref)
			throws IOException {
		FileSystem fs = FileSystem.get(conf);
		if (!fs.isDirectory(toPath))
			fs.mkdirs(toPath);
		FileStatus[] fileStatus = fs.listStatus(fromPath);
		for (FileStatus temp : fileStatus) {
			if (temp.getPath().getName().startsWith(srcPref)) {
				if (temp.getLen() > 0) {
					Path p = new Path(toPath, desPref + "_" + temp.getPath().getName());
					if (fs.exists(p)) {
						fs.delete(p, true);
					}
					fs.rename(temp.getPath(), p);
				} else {
					fs.delete(temp.getPath(), true);
				}
			}
		}
	}

	/**
	 * 提供精确的乘法运算
	 * @param d1 被乘数
	 * @param d2 乘数
	 * @return
	 */
	public static double mul(double d1, double d2){
		BigDecimal b1 = new BigDecimal(Double.toString(d1));
		BigDecimal b2 = new BigDecimal(Double.toString(d2));
		return b1.multiply(b2).doubleValue();
	}
	/**
	 * 除法运算 除不尽时scale指定四舍五入位数
	 * @param d1 被除数
	 * @param d2 除数
	 * @param scale 表示需要精确到小数点以后几位 
	 * @return
	 */
	public static double div(double d1, double d2, int scale){
		if(scale<0){
			throw new IllegalArgumentException("The scale must be a positive integer or zero!");
		}
		BigDecimal b1 = new BigDecimal(Double.toString(d1));
		BigDecimal b2 = new BigDecimal(Double.toString(d2));
		
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	/**
	 * 精确的小数位四舍五入处理
	 * @param v 需要四舍五入的数字
	 * @param scale 小数点后保留几位
	 * @return 四舍五入的结果
	 */
	public static double round(double v, int scale){
		if(scale<0){
			throw new IllegalArgumentException("The scale must be a positive integer or zero!");
		}
		BigDecimal b = new BigDecimal(Double.toString(v));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	//非整除则进位
	public int round(int i1,int i2){
  	  int modi = 0;
  	  modi = i1 % i2;
  	  int i = i1/i2;
  	  if(modi==0)
  		  return i;
  	  else
  		  return i+1;
    }
	
	/**
     * Computes the Entropy
     * @param counts   counts[i] = numInstances with label i
     * @param dataSize numInstances
     */
	@SuppressWarnings("unused")
	private static double entropy(int[] counts, int dataSize) {
		final double LOG2 = Math.log(2.0);
		if (dataSize == 0) {
			return 0.0;
		}

		double entropy = 0.0;
		double invDataSize = 1.0 / dataSize;

		for (int count : counts) {
			if (count == 0) {
				continue; // otherwise we get a NaN
			}
			double p = count * invDataSize;
			entropy += -p * Math.log(p) / LOG2;
		}

		return entropy;
	}
}
