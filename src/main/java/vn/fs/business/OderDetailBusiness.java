package vn.fs.business;

import java.util.List;

import vn.fs.entity.OrderDetail;

public interface OderDetailBusiness {

	List<OrderDetail> getByOrderId(Long orderId);
}
