package com.example.lab.service.impl;

import com.example.lab.pojo.ResultMessage;
import com.example.lab.pojo.UserRole;
import com.example.lab.pojo.entity.*;
import com.example.lab.repository.StudentRepository;
import com.example.lab.repository.TeacherRepository;
import com.example.lab.repository.UserRepository;
import com.example.lab.service.CommonService;
import com.example.lab.service.MajorService;
import com.example.lab.service.SchoolService;
import com.example.lab.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// 用户的增删改查服务
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserRepository userRepository;

    @Resource
    private TeacherRepository teacherRepository;

    @Resource
    private StudentRepository studentRepository;

    @Resource
    private SchoolService schoolService;

    @Resource
    private MajorService majorService;

    @Resource
    private CommonService commonService;

    @Override
    public ResultMessage addUser(User user) {
        ResultMessage resultMessage;
        if (findUserByUserId(user.getUserId()) != null) {
            resultMessage = ResultMessage.EXIST;
        } else if (user.getRole() == null || user.getRole() == UserRole.ADMIN || !commonService.isMatchSchoolAndMajor(user.getSchool(), user.getMajor())) {
            resultMessage = ResultMessage.FAILED;
        } else {
            try {
                if (user.getRole() == UserRole.TEACHER) {
                    teacherRepository.save(new Teacher(user));
                    resultMessage = ResultMessage.SUCCESS;
                } else if (user.getRole() == UserRole.STUDENT) {
                    studentRepository.save(new Student(user));
                    resultMessage = ResultMessage.SUCCESS;
                } else {
                    resultMessage = ResultMessage.FAILED;
                }
            } catch (Exception exception) {
                resultMessage = ResultMessage.FAILED;
            }
        }
        return resultMessage;
    }

    @Override
    public ResultMessage BatchImportUser(MultipartFile file) {
        User user = new User();
        try {
            Boolean numberFormatException = false;
            String line;
            String wrong;
            //存储错误用户的信息及相关错误
            List<String> list = new ArrayList<String>();
            BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), "GBK"));
            //首行列标题
            line=reader.readLine();
            wrong= Arrays.toString(line.split("\n")).replace("[","").replace("]","");
            list.add(wrong +",错误原因\n");
            while((line = reader.readLine())!= null){
                String []item = line.split(",");
                if (item.length <10){
                    wrong= Arrays.toString(line.split("\n")).replace("[","").replace("]","");
                    list.add(wrong +",必填项缺失\n");
                }
                else {
                    if(item[0].length()!=0&&item[1].length()!=0&&item[2].length()!=0&&item[3].length()!=0&&item[4].length()!=0&&item[7].length()!=0)
                    {
                        boolean number1=false,number2 = false,number3=false,charcheck=false,statuscheck=false,match=false;
                        if(item[2].equals("TEACHER")||item[2].equals("STUDENT"))
                        {
                            if(UserRole.valueOf(item[2])==UserRole.TEACHER)
                            {
                                number1=item[0].matches("^[0-9]*$");
                                if(!number1||item[0].length()!=8)
                                {
                                    wrong= Arrays.toString(line.split("\n")).replace("[","").replace("]","");
                                    list.add(wrong +",工号需为8位纯数字\n");
                                    continue;
                                }
                            }
                            else if(UserRole.valueOf(item[2])==UserRole.STUDENT)
                            {
                                number1=item[0].matches("^[0-9]*$");
                                if(!number1||item[0].length()!=6)
                                {
                                    wrong= Arrays.toString(line.split("\n")).replace("[","").replace("]","");
                                    list.add(wrong +",学号需为6位纯数字\n");
                                    continue;
                                }
                            }
                        }
                        else
                        {
                            wrong= Arrays.toString(line.split("\n")).replace("[","").replace("]","");
                            list.add(wrong +",角色输入不正确\n");
                            continue;
                        }
                        if(item[4].length()==18)
                            number2=item[4].matches("^[0-9]*$");
                        if(!number2)
                        {
                            wrong= Arrays.toString(line.split("\n")).replace("[","").replace("]","");
                            list.add(wrong +",身份证号需为18位纯数字\n");
                            continue;
                        }
                        if(item[5].length()==11)
                            number3=item[5].matches("^[0-9]*$");
                        else if(item[5].length()==0)
                            number3=true;
                        if(!number3)
                        {
                            wrong= Arrays.toString(line.split("\n")).replace("[","").replace("]","");
                            list.add(wrong +",手机号需为1开头的11位纯数字\n");
                            continue;
                        }
                        charcheck=item[3].matches("^[a-zA-Z\u4e00-\u9fa5]+$");
                        if(!charcheck)
                        {
                            wrong= Arrays.toString(line.split("\n")).replace("[","").replace("]","");
                            list.add(wrong +",姓名输入只能为中英文\n");
                            continue;
                        }
                        if(item[7].equals("TRUE")||item[7].equals("FALSE"))
                            statuscheck=true;
                        if(!statuscheck)
                        {
                            wrong= Arrays.toString(line.split("\n")).replace("[","").replace("]","");
                            list.add(wrong +",状态输入不正确\n");
                            continue;
                        }
                        if(commonService.isMatchSchoolAndMajor(schoolService.findSchoolBySchoolId(Integer.valueOf(item[8])),majorService.findMajorByMajorId(Integer.valueOf(item[9]))))
                            match=true;
                        if(!match)
                        {
                            wrong= Arrays.toString(line.split("\n")).replace("[","").replace("]","");
                            list.add(wrong +",学院与专业不存在或不匹配\n");
                            continue;
                        }
                        if(number1&&number2&&number3&&charcheck&&statuscheck&&match)
                        {
                            user.setUserId(Integer.valueOf(item[0]));
                            user.setPassword(item[1]);
                            user.setUsername(item[3]);
                            user.setIdNumber(item[4]);
                            user.setPhoneNumber(item[5]);
                            user.setEmail(item[6]);
                            user.setStatus(Boolean.valueOf(item[7]));
                            user.setSchool(schoolService.findSchoolBySchoolId(Integer.valueOf(item[8])));
                            user.setMajor(majorService.findMajorByMajorId(Integer.valueOf(item[9])));
                            user.setRole(UserRole.valueOf(item[2]));
                            addUser(user);
                        }
                    }
                    else{
                        wrong= Arrays.toString(line.split("\n")).replace("[","").replace("]","");
                        list.add(wrong +",必填项缺失\n");
                    }
                }
            }
            reader.close();
            System.out.println(list);
            //
//            if(list.size()!=1)
//            {
//                StringBuffer sb = new StringBuffer((CharSequence) list);
//                createCSV(sb);
//            }
        } catch (Exception e) {
                return ResultMessage.FAILED;
        }
        return ResultMessage.SUCCESS;
    }

    public void createCSV(StringBuffer sb) throws Exception {
        PrintWriter os =null;
        try {
            String fileName = "错误信息"+System.currentTimeMillis() + ".csv";
            fileName=new String(fileName.getBytes("UTF-8"),"ISO-8859-1");
            OutputStream outputStream = null;
            outputStream.write(new   byte []{( byte ) 0xEF ,( byte ) 0xBB ,( byte ) 0xBF });
            os = new PrintWriter(new OutputStreamWriter(outputStream,"UTF-8"));
            os.print(sb);
            os.flush();
        }finally{
            try {
                assert os != null;
                os.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public ResultMessage deleteUser(Integer userId) {
        ResultMessage resultMessage;
        if (findUserByUserId(userId) == null) {
            resultMessage = ResultMessage.NOTFOUND;
        } else {
            try {
                if(findTeacherByTeacherId(userId) != null) {
                    teacherRepository.deleteById(userId);
                    resultMessage = ResultMessage.SUCCESS;
                } else if (findStudentByStudentId(userId) != null) {
                    studentRepository.deleteById(userId);
                    resultMessage = ResultMessage.SUCCESS;
                } else {
                    resultMessage = ResultMessage.FAILED;
                }
            } catch (Exception exception) {
                resultMessage = ResultMessage.FAILED;
            }
        }
        return resultMessage;
    }

    @Override
    public ResultMessage updateUser(User user) {
        ResultMessage resultMessage;
        if (findUserByUserId(user.getUserId()) == null) {
            resultMessage = ResultMessage.NOTFOUND;
        } else {
            if (!commonService.isMatchSchoolAndMajor(user.getSchool(), user.getMajor())) {
                resultMessage = ResultMessage.FAILED;
            } else {
                try {
                    if (user.getRole() == UserRole.TEACHER) {
                        teacherRepository.save(new Teacher(user));
                        resultMessage = ResultMessage.SUCCESS;
                    } else if (user.getRole() == UserRole.STUDENT) {
                        studentRepository.save(new Student(user));
                        resultMessage = ResultMessage.SUCCESS;
                    } else {
                        resultMessage = ResultMessage.FAILED;
                    }
                } catch (Exception exception) {
                    resultMessage = ResultMessage.FAILED;
                }
            }
        }
        return resultMessage;
    }

    // 查询全部用户
    @Override
    public List<User> findAllUser() {
        return userRepository.findAll();
    }

    // 查询全部教师
    @Override
    public List<Teacher> findAllTeacher() {
        return teacherRepository.findAll();
    }

    // 查询全部学生
    @Override
    public List<Student> findAllStudent() {
        return studentRepository.findAll();
    }

    // 通过id查询用户
    @Override
    public User findUserByUserId(Integer userId) {
        return userRepository.findById(userId).orElse(null);
    }

    // 通过姓名查询用户
    @Override
    public List<User> findUserByUserName(String username) {
        return userRepository.findAllByUsername(username);
    }

    // 通过id查询教师
    @Override
    public Teacher findTeacherByTeacherId(Integer teacherId) {
        return teacherRepository.findById(teacherId).orElse(null);
    }

    // 通过id查询学生
    @Override
    public Student findStudentByStudentId(Integer studentId) {
        return studentRepository.findById(studentId).orElse(null);
    }
}
