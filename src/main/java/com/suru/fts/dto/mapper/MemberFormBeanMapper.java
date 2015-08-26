package com.suru.fts.dto.mapper;

import org.springframework.stereotype.Component;

import com.suru.fts.dto.MemberFormBean;
import com.suru.fts.mongo.domain.Member;
import com.suru.fts.util.BeanMapper;

@Component
public class MemberFormBeanMapper extends BeanMapper<MemberFormBean, Member> {

	@Override
	public MemberFormBean mapToBean(Member member) {
		MemberFormBean memberForm = new MemberFormBean();
		memberForm.setMemberId(member.getMemberId());
		memberForm.setDeleteHref("/admin/group/" + member.getFeatureGroupName() + "/member/"
				+ member.getMemberId() + "/delete");
		return memberForm;
	}
}
