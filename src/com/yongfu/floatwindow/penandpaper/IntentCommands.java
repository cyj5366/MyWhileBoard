package com.yongfu.floatwindow.penandpaper;


public   enum IntentCommands
{
 // static
 // {
	  
	
	command("command", 0),
    start("start", 1),
    hide("hide", 2),
    stop("stop", 3),
    toggle("toggle", 4),
    
    none("none", 5),
     help("help", 6),
     whatsnew("whatsnew", 7),
	touch("touch",8);
	
	
    
	private static IntentCommands[] ENUM$VALUES;

	private IntentCommands(String name, int index)
	  {
		  this.index = index;
		  this.name = name;
	  }
	private int index;
	private String name;
	
	  static{
	IntentCommands [] arrayOfIntentCommands = new IntentCommands[8];
    arrayOfIntentCommands[0] = command;
    arrayOfIntentCommands[1] = start;
    arrayOfIntentCommands[2] = hide;
    arrayOfIntentCommands[3] = stop;
    arrayOfIntentCommands[4] = toggle;
    arrayOfIntentCommands[5] = none;
    arrayOfIntentCommands[6] = help;
    arrayOfIntentCommands[7] = whatsnew;
      ENUM$VALUES = arrayOfIntentCommands;
  }
}
