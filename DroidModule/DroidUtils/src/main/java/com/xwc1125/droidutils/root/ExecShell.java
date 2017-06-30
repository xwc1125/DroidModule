/**
 * <p>
 * Title: ExecShell.java
 * </p>
 * <p>
 * Description: TODO(describe the file) 
 * </p>
 * <p>
 * 
 * </p>
 * @Copyright: Copyright (c) 2016年8月31日
 * @author xwc1125
 * @date 2016年8月31日 下午4:56:09
 * @version V1.0
 */
package com.xwc1125.droidutils.root;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import android.util.Log;

/**
 * <p>
 * Title: ExecShell
 * </p>
 * <p>
 * Description: TODO(describe the types) 
 * </p>
 * <p>
 * 
 * </p>
 * @author xwc1125
 * @date 2016年8月31日 下午4:56:09
 *  
 */
public class ExecShell {

	String LOG_TAG =ExecShell.class.getName();
    public static enum SHELL_CMD {
        check_su_binary(new String[] {"/system/xbin/which","su"});
 
        String[] command;
 
        SHELL_CMD(String[] command){
            this.command = command;
        }
    }
 
    public ArrayList<String> executeCommand(SHELL_CMD shellCmd){
        String line = null;
        ArrayList<String> fullResponse = new ArrayList<String>();
        Process localProcess = null;
 
        try {
            localProcess = Runtime.getRuntime().exec(shellCmd.command);
        } catch (Exception e) {
            return null;
            //e.printStackTrace();
        }
 
        @SuppressWarnings("unused")
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(localProcess.getOutputStream()));
        BufferedReader in = new BufferedReader(new InputStreamReader(localProcess.getInputStream()));
 
        try {
            while ((line = in.readLine()) != null) {
                Log.d(LOG_TAG, "--> Line received: " + line);
                fullResponse.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
 
        Log.d(LOG_TAG, "--> Full response was: " + fullResponse);
 
        return fullResponse;
    }

}
