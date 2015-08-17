package com.mstar.sourcec.control;

/**
 * jack.chen add 20150813  for control inputsource
 */
import android.util.Log;

import com.mstar.android.tvapi.common.TvManager;
import com.mstar.android.tvapi.common.exception.TvCommonException;
import com.mstar.android.tvapi.common.vo.TvOsType.EnumInputSource;


public class InputSourceControl {
	private static InputSourceControl instance;
	public enum MSTAR_INPUTSOURCE {
		E_INPUT_SOURCE_STORAGE,
		E_INPUT_SOURCE_HDMI1,
		E_INPUT_SOURCE_HDMI2,
		E_INPUT_SOURCE_HDMI3,
		E_INPUT_SOURCE_VGA,
		E_INPUT_SOURCE_NONE
	}
	void InputSourceControl()
	{
		
	}
	
	
	  public static InputSourceControl getInstance()
	  {
	    if (instance != null)
	      return instance;
	    else
	    	instance = new InputSourceControl();
	    return instance;
	  }
	  
	  public boolean changeInputSource(MSTAR_INPUTSOURCE input)
	  {
		  Log.d("jack.chen","InputSourceControl.java changeInputSource() input="+input.toString());
		  switch(input)
		  {
		  	case E_INPUT_SOURCE_HDMI1:
		  		setInputsource(EnumInputSource.E_INPUT_SOURCE_HDMI);
		  		break;
		  	case E_INPUT_SOURCE_HDMI2:
		  		setInputsource(EnumInputSource.E_INPUT_SOURCE_HDMI2);
		  		break;
		  	case E_INPUT_SOURCE_HDMI3:
		  		setInputsource(EnumInputSource.E_INPUT_SOURCE_HDMI3);
		  		break;
		  	case E_INPUT_SOURCE_VGA:
		  		setInputsource(EnumInputSource.E_INPUT_SOURCE_VGA);
		  		break;
		  	case E_INPUT_SOURCE_STORAGE:
		  		setInputsource(EnumInputSource.E_INPUT_SOURCE_STORAGE);
		  		break;
			default:
				setInputsource(EnumInputSource.E_INPUT_SOURCE_STORAGE);
				  break;
		  }
		  return true;
	  }
	  public boolean setInputsource(EnumInputSource input)
	  {
		  Log.d("jack.chen","InputSourceControl.java changeInputSource() input="+input.toString());
		  boolean result = false;
			try {
				result =  TvManager.getInstance().setInputSource(input);
			} catch (TvCommonException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return result;
	  }
	 
}
