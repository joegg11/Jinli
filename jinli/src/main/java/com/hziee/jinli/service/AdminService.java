package com.hziee.jinli.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hziee.jinli.enums.AdminExceptionEnum;
import com.hziee.jinli.enums.PermissionExceptionEnum;
import com.hziee.jinli.enums.VIPExceptionEnum;
import com.hziee.jinli.exception.JlException;
import com.hziee.jinli.mapper.AdminMapper;
import com.hziee.jinli.mapper.ClubMapper;
import com.hziee.jinli.mapper.PermissionMapper;
import com.hziee.jinli.mapper.RoleMapper;
import com.hziee.jinli.pojo.*;
import com.hziee.jinli.utils.EncryptUtil;
import com.hziee.jinli.vo.AdminLoginInfo;
import com.hziee.jinli.vo.PermissionBusinessObject;
import com.hziee.jinli.vo.PageResult;
//import org.apache.catalina.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

import static com.hziee.jinli.utils.EncryptUtil.String_Encrypt;

@Service
public class AdminService {

    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    private ClubMapper clubMapper;

    //员工添加
    public void addAdmin(int id,Admin admin){
        Admin theAdmin= adminMapper.selectByPrimaryKey(id);
        Role theRole = roleMapper.selectByPrimaryKey(theAdmin.getRole());
        Permission permissionTool = new Permission();
        permissionTool.setPermissionId(theRole.getPermissionId());
        Permission thePermission = permissionMapper.selectOne(permissionTool);
        PermissionBusinessObject permissionBo = new PermissionBusinessObject(thePermission);
        if(permissionBo.getPermissioninfo().getAdminInfoChange()) {
            admin.setAdminId(null);
            admin.setPwd(String_Encrypt(admin.getPwd()));
            admin.setClubId(theAdmin.getClubId());
            Admin adminTool = new Admin();
            adminTool.setName(admin.getName());
            Admin theAdmin1 = adminMapper.selectOne(adminTool);
            if(theAdmin1 != null) throw new JlException(AdminExceptionEnum.ADMIN_CREATE_NAME_REPETITIVE_ERROR);
            int count = adminMapper.insert(admin);
            if (count != 1) {
                throw new JlException(AdminExceptionEnum.ADMIN_CREATE_ERROR);
            }
        }else{
            throw new JlException(PermissionExceptionEnum.ADMIN_NO_SUCH_PERMISSION);
        }
    }

    //员工删除
    public void deleteAdmin(int id,int deletedAdminId){
        Admin theAdmin = adminMapper.selectByPrimaryKey(id);
        Admin deletedAdmin = adminMapper.selectByPrimaryKey(deletedAdminId);
        Role theRole = roleMapper.selectByPrimaryKey(theAdmin.getRole());
        Permission permissionTool = new Permission();
        permissionTool.setPermissionId(theRole.getPermissionId());
        Permission thePermission = permissionMapper.selectOne(permissionTool);
        PermissionBusinessObject permissionBo = new PermissionBusinessObject(thePermission);
        if(permissionBo.getPermissioninfo().getAdminInfoChange() && (theAdmin.getClubId().equals(deletedAdmin.getClubId()))) {//一个会所的才有权限删除
            int count = adminMapper.deleteByPrimaryKey(deletedAdminId);
            if (count != 1) {
                throw new JlException(AdminExceptionEnum.ADMIN_DELETE_ERROR);
            }
        }else{
            throw new JlException(PermissionExceptionEnum.ADMIN_NO_SUCH_PERMISSION);
        }
    }
    //员工密码重置
    public String resetPasswordAdmin(int id,int resetAdminId){
        Admin theAdmin = adminMapper.selectByPrimaryKey(id);
        Admin resetAdmin = adminMapper.selectByPrimaryKey(resetAdminId);
        Role theRole = roleMapper.selectByPrimaryKey(theAdmin.getRole());
        PermissionBusinessObject thePermissionBusinessObject = new PermissionBusinessObject(permissionMapper.selectByPrimaryKey(theRole.getPermissionId()));
        if(thePermissionBusinessObject.getPermissioninfo().getAdminInfoSelect() && theAdmin.getClubId()==resetAdmin.getClubId()){
            int newPasswordInt = (int)(Math.random()*1000000);
            String newPasswordString = EncryptUtil.String_Encrypt(Integer.toString(newPasswordInt)).substring(0,6);
            System.out.println(newPasswordString);
            resetAdmin.setPwd(EncryptUtil.String_Encrypt(newPasswordString));
            int count = adminMapper.updateByPrimaryKey(resetAdmin);
            if(count != 1) throw new JlException(AdminExceptionEnum.ADMIN_UPDATE_ERROR);
            return newPasswordString;
        }
        else throw new JlException(PermissionExceptionEnum.ADMIN_NO_SUCH_PERMISSION);
    }

    //员工信息修改
    public void updateAdmin(int id,int updateAdminId,Admin admin){
        Admin theAdmin = adminMapper.selectByPrimaryKey(id);
        Admin updateAdmin = adminMapper.selectByPrimaryKey(updateAdminId);
        Role roleTool = new Role();
        roleTool.setRoleId(theAdmin.getRole());
        Role theRole = roleMapper.selectOne(roleTool);
        Permission permissionTool = new Permission();
        permissionTool.setPermissionId(theRole.getPermissionId());
        Permission thePermission = permissionMapper.selectOne(permissionTool);
        PermissionBusinessObject permissionBo = new PermissionBusinessObject(thePermission);
        if(permissionBo.getPermissioninfo().getAdminInfoChange() && theAdmin.getClubId().equals(updateAdmin.getClubId())) {//一个会所的才有权修改信息
            admin.setAdminId(updateAdminId);
            admin.setPwd(String_Encrypt(admin.getPwd()));
            admin.setClubId(updateAdmin.getClubId());
            int count = adminMapper.updateByPrimaryKeySelective(admin);
            if (count != 1) {
                throw new JlException(AdminExceptionEnum.ADMIN_UPDATE_ERROR);
            }
        }else{
            throw new JlException(PermissionExceptionEnum.ADMIN_NO_SUCH_PERMISSION);
        }
    }

    //用户login
    public AdminLoginInfo adminLogin(String name, String password){
        if(name.equals("")) throw new JlException(AdminExceptionEnum.ADMIN_USERNAME_NULL_ERROR);
        if(password.equals("")) throw new JlException(AdminExceptionEnum.ADMIN_PASSWORD_NULL_ERROR);
        Admin adminTool = new Admin();
        adminTool.setName(name);
        Admin theAdmin = adminMapper.selectOne(adminTool);
        if (theAdmin.getPwd().equals(String_Encrypt(password))){
            Club clubTool = new Club();
            clubTool.setClubId(theAdmin.getClubId());
            Club theClub = clubMapper.selectByPrimaryKey(theAdmin.getClubId());
            Role theRole = roleMapper.selectByPrimaryKey(theAdmin.getRole());
            return new AdminLoginInfo(theAdmin,theClub.getClubName(),theClub.getClubType(),theRole.getRoleName());
        }
        throw new JlException(AdminExceptionEnum.ADMIN_LOGIN_ERROR);
    }

    //根据id查询用户
    public Admin selectAdminById(int pid, int id){
        /*Admin new_admin = new Admin();
        new_admin.setAdminId(id);
        Admin mid = adminMapper.selectOne(new_admin);*/

        Admin theAdmin = adminMapper.selectByPrimaryKey(pid);
        Role theRole = roleMapper.selectByPrimaryKey(theAdmin.getRole());
        Permission permissionTool = new Permission();
        permissionTool.setPermissionId(theRole.getPermissionId());
        Permission thePermission = permissionMapper.selectOne(permissionTool);
        PermissionBusinessObject permissionBo = new PermissionBusinessObject(thePermission);
        if (permissionBo.getPermissioninfo().getAdminInfoSelect()){
            Admin mid = adminMapper.selectByPrimaryKey(id);
            if(mid.getAdminId() != 0 && mid.getClubId() == theAdmin.getClubId()) {
                mid.setPwd(null);
                return mid;
            }else
                throw new JlException(AdminExceptionEnum.ADMIN_NOT_FOUND);
            /*if (){

            }*/
        }else {
            throw new JlException(PermissionExceptionEnum.ADMIN_NO_SUCH_PERMISSION);
        }

    }

    //根据name查询用户
    public Admin selectAdminByName(int id, String name){
        /*Admin new_admin = new Admin();
        new_admin.setName(name);
        Admin admin = adminMapper.selectOne(new_admin);

        return admin;*/

        Admin theAdmin = adminMapper.selectByPrimaryKey(id);
        Role roleTool = new Role();
        roleTool.setRoleId(theAdmin.getRole());
        Role theRole = roleMapper.selectOne(roleTool);
        Permission permissionTool = new Permission();
        permissionTool.setPermissionId(theRole.getPermissionId());
        Permission thePermission = permissionMapper.selectOne(permissionTool);
        PermissionBusinessObject permissionBo = new PermissionBusinessObject(thePermission);
        if(permissionBo.getPermissioninfo().getAdminInfoSelect()){

            Admin new_admin = new Admin();
            new_admin.setName(name);
            Admin admin = adminMapper.selectOne(new_admin);

            if(admin!=null && admin.getClubId() == theAdmin.getClubId()) {
                admin.setPwd(null);
                return admin;
            }
            else
                throw new JlException(AdminExceptionEnum.ADMIN_NOT_FOUND);

        }else {
            throw new JlException(PermissionExceptionEnum.ADMIN_NO_SUCH_PERMISSION);
        }


    }

    //根据club_id查询用户
    public List<Admin> selectAdminByClubId(int id, int club_id){
       /* Admin new_admin = new Admin();
        new_admin.setClubId(club_id);
        List<Admin> admin = adminMapper.select(new_admin);
        return admin;*/
        Admin AdminTool = new Admin();
        AdminTool.setAdminId(id);
        Admin theAdmin = adminMapper.selectOne(AdminTool);
        Role roleTool = new Role();
        roleTool.setRoleId(theAdmin.getRole());
        Role theRole = roleMapper.selectOne(roleTool);
        Permission permissionTool = new Permission();
        permissionTool.setPermissionId(theRole.getPermissionId());
        Permission thePermission = permissionMapper.selectOne(permissionTool);
        PermissionBusinessObject permissionBo = new PermissionBusinessObject(thePermission);
        if (permissionBo.getPermissioninfo().getAdminInfoSelect()){
            Admin new_admin = new Admin();
            new_admin.setClubId(club_id);
            List<Admin> admin = adminMapper.select(new_admin);
            if(CollectionUtils.isEmpty(admin)){
                throw new JlException(AdminExceptionEnum.ADMIN_NOT_FOUND);
            }
            for(int i = 0;i < admin.size();i++) admin.get(i).setPwd(null);
            return admin;
        }else {
            throw new JlException(PermissionExceptionEnum.ADMIN_NO_SUCH_PERMISSION);
        }

    }

    //根据club_name查询用户
    public List<Admin> selectAdminByClubName(int id, String club_Name){
        /*Admin new_admin = new Admin();
        new_admin.setName(club_Name);
        List<Admin> admin = adminMapper.select(new_admin);
        return admin;*/

        Admin theAdmin = adminMapper.selectByPrimaryKey(id);
        Role roleTool = new Role();
        roleTool.setRoleId(theAdmin.getRole());
        Role theRole = roleMapper.selectOne(roleTool);
        Permission permissionTool = new Permission();
        permissionTool.setPermissionId(theRole.getPermissionId());
        Permission thePermission = permissionMapper.selectOne(permissionTool);
        PermissionBusinessObject permissionBo = new PermissionBusinessObject(thePermission);
        if (permissionBo.getPermissioninfo().getAdminInfoSelect()){
            Club clubTool = new Club();
            clubTool.setClubName(club_Name);
            Club theClub = clubMapper.selectOne(clubTool);
            Admin new_admin = new Admin();
            new_admin.setClubId(theClub.getClubId());
            List<Admin> admin = adminMapper.select(new_admin);
            if(CollectionUtils.isEmpty(admin)){
                throw new JlException(AdminExceptionEnum.ADMIN_NOT_FOUND);
            }
            for(int i = 0;i < admin.size();i++) admin.get(i).setPwd(null);
            return admin;
        }else {
            throw new JlException(PermissionExceptionEnum.ADMIN_NO_SUCH_PERMISSION);
        }


    }

    //根据telephone查询用户
    public Admin selectAdminByTel(int id, Long tel){
        /*Admin new_admin = new Admin();
        new_admin.setTelephone(tel);
        Admin admins = adminMapper.selectOne(new_admin);
        return admins;*/

        Admin AdminTool = new Admin();
        AdminTool.setAdminId(id);
        Admin theAdmin = adminMapper.selectOne(AdminTool);
        Role roleTool = new Role();
        roleTool.setRoleId(theAdmin.getRole());
        Role theRole = roleMapper.selectOne(roleTool);
        Permission permissionTool = new Permission();
        permissionTool.setPermissionId(theRole.getPermissionId());
        Permission thePermission = permissionMapper.selectOne(permissionTool);
        PermissionBusinessObject permissionBo = new PermissionBusinessObject(thePermission);
        if (permissionBo.getPermissioninfo().getAdminInfoSelect()){
            Admin new_admin = new Admin();
            new_admin.setTelephone(tel);
            Admin admin = adminMapper.selectOne(new_admin);
            if(admin.getAdminId() != 0 && admin.getClubId() == theAdmin.getClubId())
                return admin;
            else
                throw new JlException(AdminExceptionEnum.ADMIN_NOT_FOUND);
        }else {
            throw new JlException(PermissionExceptionEnum.ADMIN_NO_SUCH_PERMISSION);
        }
    }

    //根据role查询
    public List<Admin> selectAdminListByRole(int id, int role){

        /*Admin new_admin = new Admin();
        new_admin.setRole(role);
        List<Admin> list = adminMapper.select(new_admin);
        if(CollectionUtils.isEmpty(list)){
            throw new JlException(AdminExceptionEnum.ADMIN_NOT_FOUND);
        }
        return list;*/

        Admin AdminTool = new Admin();
        AdminTool.setAdminId(id);
        Admin theAdmin = adminMapper.selectOne(AdminTool);
        Role roleTool = new Role();
        roleTool.setRoleId(theAdmin.getRole());
        Role theRole = roleMapper.selectOne(roleTool);
        Permission permissionTool = new Permission();
        permissionTool.setPermissionId(theRole.getPermissionId());
        Permission thePermission = permissionMapper.selectOne(permissionTool);
        PermissionBusinessObject permissionBo = new PermissionBusinessObject(thePermission);
        if (permissionBo.getPermissioninfo().getAdminInfoSelect()){
            Admin new_admin = new Admin();
            new_admin.setRole(role);
            new_admin.setClubId(theAdmin.getClubId());
            List<Admin> admins = adminMapper.select(new_admin);
            if(CollectionUtils.isEmpty(admins)){
                throw new JlException(AdminExceptionEnum.ADMIN_NOT_FOUND);
            }
            for(int i = 0;i < admins.size();i++) admins.get(i).setPwd(null);
            return admins;
        }else {
            throw new JlException(PermissionExceptionEnum.ADMIN_NO_SUCH_PERMISSION);
        }
    }

    //返回所有用户信息
    public PageResult<Admin> selectAllAdminListByPage(Integer page, Integer rows, String sortBy, Boolean desc, int id){
        Admin AdminTool = new Admin();
        AdminTool.setAdminId(id);
        Admin theAdmin = adminMapper.selectOne(AdminTool);
        Role roleTool = new Role();
        roleTool.setRoleId(theAdmin.getRole());
        Role theRole = roleMapper.selectOne(roleTool);
        Permission permissionTool = new Permission();
        permissionTool.setPermissionId(theRole.getPermissionId());
        Permission thePermission = permissionMapper.selectOne(permissionTool);
        PermissionBusinessObject permissionBo = new PermissionBusinessObject(thePermission);
        if (permissionBo.getPermissioninfo().getAdminInfoSelect()) {
            PageHelper.startPage(page, rows);
            Admin admin = new Admin();
            admin.setAdminId(id);
            Admin the_admin = adminMapper.selectOne(admin);
            int role = the_admin.getRole();
            List<Admin> admin_list = new ArrayList<Admin>(), all_admin_list;
            all_admin_list = foreachAdmin(sortBy, desc);
            for (int i = 0; i < all_admin_list.size(); i++) {
                if (all_admin_list.get(i).getRole() > role && all_admin_list.get(i).getClubId() == theAdmin.getClubId()) {
                    admin_list.add(all_admin_list.get(i));
                }
            }
            if (CollectionUtils.isEmpty(admin_list)) {
                throw new JlException(AdminExceptionEnum.ADMIN_NOT_FOUND);
            }
            PageInfo<Admin> info = new PageInfo<Admin>(admin_list);
            return new PageResult<Admin>(info.getTotal(), admin_list);
        }else{
            throw new JlException(PermissionExceptionEnum.ADMIN_NO_SUCH_PERMISSION);
        }
    }

    //遍历
    public List<Admin> foreachAdmin(String sortBy,boolean desc){
        Example example = new Example(Admin.class);
        if (StringUtils.isNoneBlank(sortBy)){
            String orderByClause = sortBy + (desc ? "DESC" : "ASC");
            example.setOrderByClause(orderByClause);
        }
        List<Admin> list = adminMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(list)) {
            throw new JlException(AdminExceptionEnum.ADMIN_NOT_FOUND);
        }
        return list;
    }

    //分页呈现用户数据
    public PageResult<Admin> selectBrandByPage(Integer page, Integer rows, String sortBy, Boolean desc, String key){
        //分页
        PageHelper.startPage(page, rows);
        //过滤
        Example example = new Example(Admin.class);
        if (StringUtils.isNoneBlank(key)){
            //只要满足其一，就符合用户搜索的条件，就会呈现数据
            example.createCriteria().orLike("name", "%"+key+"%");
        }
        //排序
        if (StringUtils.isNoneBlank(sortBy)){
            String orderByClause = sortBy + (desc ? "DESC" : "ASC");
            example.setOrderByClause(orderByClause);
        }
        //查询
        List<Admin> list = adminMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(list)){
            throw new JlException(AdminExceptionEnum.ADMIN_NOT_FOUND);
        }

        //解析分页结果
        PageInfo<Admin> info = new PageInfo<Admin>(list);
        return new PageResult<Admin>(info.getTotal(), list);
    }
}
