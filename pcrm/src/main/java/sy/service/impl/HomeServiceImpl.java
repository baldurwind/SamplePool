package sy.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import sy.service.HomeServiceI;

@Service("homeService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)

public class HomeServiceImpl extends BaseServiceImpl implements HomeServiceI {

}
