package com.work.vacation.dao;

import com.work.vacation.model.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberDao {
    Member selectMember(Member param);
}
