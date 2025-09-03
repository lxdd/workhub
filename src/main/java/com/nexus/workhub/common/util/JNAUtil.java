package com.nexus.workhub.common.util;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;

import java.io.File;
import java.nio.charset.StandardCharsets;

/**
 * JNA调用动态库的工具类
 * @author li xiaodong
 * @date 2024/6/12 15:28
 */
public class JNAUtil {

     class SystemUtil {
        private static String osName = System.getProperty("os.name").toLowerCase();
    
        public static boolean isLinux() {
            return osName.indexOf("linux") > -1;
        }
    
        public static boolean isWindows() {
            return osName.indexOf("windows") > -1;
        }
    }

    public interface Libmodel extends Library {
        String LIB_NAME = SystemUtil.isLinux() ? "libmodel.so" : (SystemUtil.isWindows() ? "libmodel" : "libmodel.dylib");
        Libmodel INSTANCE = (Libmodel) Native.load("/lib" + File.separator + LIB_NAME, JNAUtil.Libmodel.class);
        String LICENSE = "xxxxxxxxxxx";

        @Structure.FieldOrder({ "length", "data" })
        class ExportString extends Structure {
            public int length;
            public Pointer data;

            public ExportString() {}
        }
        
        // 声明所有动态库中的函数
        long BuildTime();
        ExportString CreateExportString();
        void FreeExportString(ExportString export_info);
        void GetMachineID(ExportString export_info);
        void SetLicense(String license);
        
        // 调用以下的接口全部都需要先SetLicense, SetLicense调用一次之后就会全局生效
        void GetStandardInfo(String zpet, ExportString export_string);
        void GetCalculationInfo(String zpet, ExportString export_string);
        void GetMultiPlanInfo(String zpet, ExportString export_string);
        void SetProgressStr(String zpet, String progress, ExportString export_string);
        void SetWorksStatusInfo(String zpet, String info, ExportString export_string);
        void GetNewDoc(ExportString export_string);
        void GetNewDocWithParam(String time_interval, ExportString export_string);
        void GetRecentMilestone(String zpet, String time_info, ExportString export_string);
        void GetFilterWorks(String zpet, String filter_info, ExportString export_string);
        void GetFrontMilestones(String zpet, String milestone_ids, ExportString export_string);
        void GetMonthPert(String zpet, String time_interval, ExportString export_string);
        void AddLockSetting(String zpet, String setting, ExportString exportString);
        void RemoveLockSetting(String zpet, String setting, ExportString exportString);
        void ClearLockSetting(String zpet, ExportString exportString);
        void GetTotalInfo(String zpet, ExportString export_string);
        void GetResReportInfo(String zpet, ExportString export_string);
        void GetRootInfo(String zpet, ExportString export_string);
        void GetDocFromWorkSites(String work_sites, String init_param, String other_info, ExportString export_string);
        void CreateFromProject(String zpet, ExportString export_string);
        void ExtractJSONFromGzp(byte[] file, int length, ExportString export_string);
        void PackJSONToGzp(byte[] zpet, ExportString export_string);
        void BranchName(ExportString export_string);
    }

    public static String getMachineIDFromLib() {
        // 在调用以ExportString为参数的函数时要先创建ExportString
        Libmodel.ExportString exportString = Libmodel.INSTANCE.CreateExportString();
        Libmodel.INSTANCE.GetMachineID(exportString);
        String result = exportString.data.getString(0);
        // ExportString用完之后要释放
        Libmodel.INSTANCE.FreeExportString(exportString);
        return result;
    }
    
    public static String createBlankDoc() {
        Libmodel.INSTANCE.SetLicense(Libmodel.LICENSE);
        
        Libmodel.ExportString exportString = Libmodel.INSTANCE.CreateExportString();
        Libmodel.INSTANCE.GetNewDoc(exportString);
        String result = exportString.data.getString(0);
        Libmodel.INSTANCE.FreeExportString(exportString);
        return result;
    }
    
    public static String extractFromGzp(byte[] file) {
        Libmodel.INSTANCE.SetLicense(Libmodel.LICENSE);
        
        Libmodel.ExportString exportString = Libmodel.INSTANCE.CreateExportString();
        Libmodel.INSTANCE.ExtractJSONFromGzp(file, file.length, exportString);
        String result = exportString.data.getString(0);
        Libmodel.INSTANCE.FreeExportString(exportString);
        return result;
    }

    public static byte[] packToGzp(String json) {
        Libmodel.INSTANCE.SetLicense(Libmodel.LICENSE);
        
        Libmodel.ExportString exportString = Libmodel.INSTANCE.CreateExportString();
        // 注意必须UTF_8编码
        Libmodel.INSTANCE.PackJSONToGzp(json.getBytes(StandardCharsets.UTF_8), exportString);
        byte[] result = exportString.data.getByteArray(0, exportString.length);
        Libmodel.INSTANCE.FreeExportString(exportString);
        return result;
    }

    public static void main(String[] args) {
        System.out.println("machine id: " + getMachineIDFromLib());
       
    }


}
