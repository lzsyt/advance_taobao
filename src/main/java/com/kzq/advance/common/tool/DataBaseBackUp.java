package com.kzq.advance.common.tool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Controller
public class DataBaseBackUp {
    public static final String WINDOWS_UPLOAD_PATH = "D:/data/peng/dbfiles/";

    public static final String LINUX_UPLOAD_PATH = "/data/peng/dbfiles/";

    public static final String SQL_BACKUP_PREFIX_FORMAT = "yyyyMMddHHmmss";


    private Logger logger = LoggerFactory.getLogger(getClass());

    static String filePath;

    @Autowired
    private JdbcBean jdbcBean;


//    @Scheduled(cron="59 59 23 * * ? ")//表示每天晚上23点59分59秒执行一次
    @Scheduled(cron="0 13 11 ? * *")//表示每天晚上23点59分59秒执行一次
    public void backup(){
        try {
            String fileName = new SimpleDateFormat(SQL_BACKUP_PREFIX_FORMAT).format(new Date())+"_backup";
            filePath = getFilePath(fileName+".sql");
            boolean exportFlag = executeExportCommond(jdbcBean,filePath);
            if(exportFlag){
                logger.info("******************************");
                logger.info("数据库数据备份本地成功！");
                logger.info("******************************");
//                return R.ok("数据库数据备份本地成功！");
            }else{
                logger.info("******************************");
                logger.info("数据库数据备份失败");
                logger.info("******************************");
//                return R.error("数据库数据备份失败");
            }
        } catch (Exception e) {
            logger.info("运行异常，数据库数据备份失败！");
            e.printStackTrace();
//            return R.error("备份失败");
            logger.info("******************************");
            logger.info("备份失败");
            logger.info("******************************");
        }
    }


    /**
     * 导出数据(方法一)
     * dos命令执行备份 mysqldump -P port -h ip -u username -ppassWord projectName > d:\db.sql
     * C:\Program Files\MySQL\MySQL Server 5.6\bin\mysqldump -P 3306 -h rm-bp1o0s4ntr2do799nko.mysql.rds.aliyuncs.com -u xwmysqlroot -pXw68789481! xiaowei_zaixian --default-character-set=utf8 --lock-tables=false --result-file=D:/data/design_center/dbfiles/20180817185421_backup.sql
     * @param jdbcBean
     * @param filePath
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    //执行导出命令
    public static boolean executeExportCommond(JdbcBean jdbcBean,String filePath) throws IOException, InterruptedException{

        //动态获取本地MySQL数据库安装目录bin下的目录
        String C=getMysqlPath();

        //静态获取本地MySQL数据库安装目录下bin的目录
//        String C="C:\\Program Files\\MySQL\\MySQL Server 5.6\\bin\\";

        String sql = new StringBuffer("mysqldump").
                append(" -P ").append(jdbcBean.getPort()).
                append(" -h ").append(jdbcBean.getIp()).
                append(" -u ").append(jdbcBean.getUsername()).
                append(" -p").append(jdbcBean.getPassword()).
                append(" ").append(jdbcBean.getDb()).
                append(" --default-character-set=utf8 ").
                append("--lock-tables=false --result-file=").append(filePath).toString();

        System.out.println(sql);

        Process process = Runtime.getRuntime().exec(sql);
        if(process.waitFor()==0){//0 表示线程正常终止。
            return true;
        }
        return false;//异常终止
    }

    /**
     * 动态获取mysql安装路径(bin下的路径)
     *【注】:本地MySQL数据库配置环境变量(新建MYSQL_HOME变量，值为“MySQL安装路径下【bin的上一层】”，
     *                                   在Path变量中配置MYSQL_HOME变量，值为【%MYSQL_HOME%bin】)。
     * @return
     */
    public static String getMysqlPath(){
        @SuppressWarnings("rawtypes")
        Map m=System.getenv();
        String s2=(String) m.get("MYSQL_HOME");//获取本计算机环境变量中PATH的内容
        String mySqlPath=s2+"\\bin\\";
        System.out.println("MySQL本地安装路径："+mySqlPath);
        return mySqlPath;
    }


    //获得文件路径
    public static String getFilePath(String fileName){
        String os = System.getProperty("os.name"); //获取操作系统的名称
        String rootPath;
        String filPath;
        if(os.toLowerCase().startsWith("win") || os.toLowerCase().startsWith("Win")){
            rootPath = WINDOWS_UPLOAD_PATH;
        }else{
            rootPath = LINUX_UPLOAD_PATH;
        }
        if(!new File(rootPath).exists()){//判断文件是否存在
            new File(rootPath).mkdirs();//可以在不存在的目录中创建文件夹。诸如：a\\b,既可以创建多级目录。
        }
        if(fileName==null){
            filPath = rootPath+new SimpleDateFormat(SQL_BACKUP_PREFIX_FORMAT).format(new Date())+"_backup.sql";
        }else{
            filPath = rootPath+fileName;
        }
        return filPath;
    }
}