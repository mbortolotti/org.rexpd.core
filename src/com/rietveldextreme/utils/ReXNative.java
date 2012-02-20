package com.rietveldextreme.utils;

import com.rietveldextreme.optimization.FitnessFunction;

public class ReXNative {
	
	public static final String REX_DL_NAME = "rex";
	
	public enum OSType {
	    APPLE, LINUX, SUN, UNKNOWN, WINDOWS
	};
	
	public static OSType calculateOS() {
	    String osName = System.getProperty("os.name").toLowerCase();
	    assert osName != null;
	    if (osName.startsWith("mac os x")) {
	        return OSType.APPLE;
	    }
	    if (osName.startsWith("windows")) {
	        return OSType.WINDOWS;
	    }
	    if (osName.startsWith("linux")) {
	        return OSType.LINUX;
	    }
	    if (osName.startsWith("sun")) {
	        return OSType.SUN;
	    }
	    return OSType.UNKNOWN;
	}
	
	public enum ARCHType {
	    PPC, PPC_64, SPARC, UNKNOWN, X86, X86_64
	};
	
	public static ARCHType calculateArch() {
	    String osArch = System.getProperty("os.arch").toLowerCase();
	    assert osArch != null;
	    if (osArch.equals("i386")) {
	        return ARCHType.X86;
	    }
	    if (osArch.startsWith("amd64") || osArch.startsWith("x86_64")) {
	        return ARCHType.X86_64;
	    }
	    if (osArch.equals("ppc")) {
	        return ARCHType.PPC;
	    }
	    if (osArch.startsWith("ppc")) {
	        return ARCHType.PPC_64;
	    }
	    if (osArch.startsWith("sparc")) {
	        return ARCHType.SPARC;
	    }
	    return ARCHType.UNKNOWN;
	}
	
	public static final void loadRexLibrary() {
		String dlname = REX_DL_NAME;
		try {
			OSType os = calculateOS();
			ARCHType arch = calculateArch();
			System.out.println("Operating system: " + os);
			System.out.println("Processor architecture: " + arch);
			if (arch == ARCHType.X86_64)
				dlname += "_64";
			System.loadLibrary(dlname);
			System.out.println("ReX native library loaded");
		} catch (Throwable e) {
			System.out.println("Failed to load ReX native library in java library path: no " 
		+ System.mapLibraryName(dlname) + " in path: ");
			System.out.println(System.getProperty("java.class.path"));
			System.out.println(System.getProperty("java.library.path"));
			e.printStackTrace();
		}
	}

	static {
		loadRexLibrary();
	}
	
	public static native int LMminimize(double[] start_parameters, double[] measurements, double[] covariance, int iterations, FitnessFunction function);
	
	public static native int LevenbergMarquardt(double[] parameters, int ndata, int iterations, FitnessFunction function);
	
	public static native double PseudoVoigt(double eta0, double eta1, double eta2, 
			double two_theta_n, double two_theta_k, double FWHM, double sizeB, double strainB);
	
	public static native double PseudoVoigtTCH(double u, double v, double w, double x, double y, double z, 
			double two_theta_n, double two_theta_k, double sizeB, double strainB);
	
	public static native double VoigtApproxNative(double u, double v, double w, double x, double y, double z,
			double two_theta_n, double two_theta_k, double sizeB, double strainB);
	
	public static native double Caglioti(double U, double V, double W, double two_theta);
	
	public static native double tan(double angle_rad);
	public static native double sin(double angle_rad);
	public static native double cos(double angle_rad);
	public static native double sqrt(double arg);
	public static native double exp(double arg);
	public static native double log(double arg);
	public static native double pow(double x, double y);

	

	
	

}
