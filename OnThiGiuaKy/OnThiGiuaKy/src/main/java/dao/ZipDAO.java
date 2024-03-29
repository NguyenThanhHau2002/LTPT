package dao;

import java.util.ArrayList;
import static com.mongodb.client.model.Sorts.*;
import static com.mongodb.client.model.Projections.*;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import com.mongodb.reactivestreams.client.FindPublisher;
import com.mongodb.reactivestreams.client.MongoCollection;
import com.mongodb.reactivestreams.client.MongoDatabase;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;

import entities.Zips;

public class ZipDAO {
	private MongoCollection<Zips> collection;

	public ZipDAO(MongoDatabase db) {
		// TODO Auto-generated constructor stub
		collection = db.getCollection("zips", Zips.class);
	}

	public boolean insert(Zips z) {
//		Lay61 ket qua tra ve
		AtomicBoolean result = new AtomicBoolean(false);
		CountDownLatch latch = new CountDownLatch(1);
		Publisher<InsertOneResult> publicer = collection.insertOne(z);
		Subscriber<InsertOneResult> subcriber = new Subscriber<InsertOneResult>() {

			@Override
			public void onSubscribe(Subscription s) {
				// TODO Auto-generated method stub
				s.request(1);
			}

			@Override
			public void onNext(InsertOneResult t) {
				// TODO Auto-generated method stub
				System.out.println("Inserted: " + t);
//				Kiem tra thanh khong hay ko
				result.set(t.getInsertedId() != null ? true : false);
			}

			@Override
			public void onError(Throwable t) {
				// TODO Auto-generated method stub
				System.out.println("Failed");

			}

			@Override
			public void onComplete() {
				// TODO Auto-generated method stub
				System.out.println("Completed");
				latch.countDown();
			}

		};
		publicer.subscribe(subcriber);
		try {
			latch.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result.get();
	}

	public List<Zips> findAll() {
		List<Zips> result = new ArrayList<Zips>();
		CountDownLatch latch = new CountDownLatch(1);

		FindPublisher<Zips> publicer = collection.find().limit(10);
		Subscriber<Zips> subscriber = new Subscriber<Zips>() {
			private Subscription s;

			@Override
			public void onSubscribe(Subscription s) {
				// TODO Auto-generated method stub
				this.s = s;
				s.request(1);

			}

			@Override
			public void onNext(Zips t) {
				// TODO Auto-generated method stub
				result.add(t);
				this.s.request(1);
			}

			@Override
			public void onError(Throwable t) {
				// TODO Auto-generated method stub
				t.printStackTrace();

			}

			@Override
			public void onComplete() {
				// TODO Auto-generated method stub
				latch.countDown();
			}

		};
		publicer.subscribe(subscriber);
		try {
			latch.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public List<Zips> findByCity(String city) {
		List<Zips> result = new ArrayList<>();
		CountDownLatch latch = new CountDownLatch(1);

		FindPublisher<Zips> publisher = collection.find(eq("city", city));
		Subscriber<Zips> subscriber = new Subscriber<Zips>() {
			private Subscription s;

			@Override
			public void onSubscribe(Subscription s) {
				this.s = s;
				s.request(1);
			}

			@Override
			public void onNext(Zips t) {
				result.add(t);
				this.s.request(1);
			}

			@Override
			public void onError(Throwable t) {
				t.printStackTrace();
			}

			@Override
			public void onComplete() {
				latch.countDown();
			}
		};
		publisher.subscribe(subscriber);
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return result;
	}

	public boolean updateById(String id, Zips updatedZip) {
		AtomicBoolean result = new AtomicBoolean(false);
		CountDownLatch latch = new CountDownLatch(1);

		Publisher<UpdateResult> publisher = collection.updateOne(eq("_id", new ObjectId(id)),
				combine(set("city", updatedZip.getCity()), set("state", updatedZip.getState()),
						set("pop", updatedZip.getPop()), set("loc", updatedZip.getLoc())));
		Subscriber<UpdateResult> subscriber = new Subscriber<UpdateResult>() {
			private Subscription s;

			@Override
			public void onSubscribe(Subscription s) {
				this.s = s;
				s.request(1);
			}

			@Override
			public void onNext(UpdateResult t) {
				if (t.getModifiedCount() > 0) {
					result.set(true);
				}
				this.s.request(1);
			}

			@Override
			public void onError(Throwable t) {
				t.printStackTrace();
			}

			@Override
			public void onComplete() {
				latch.countDown();
			}
		};
		publisher.subscribe(subscriber);
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return result.get();
	}

	public List<Zips> findZipsWithPopulationInRange(int minPopulation, int maxPopulation) {
		List<Zips> result = new ArrayList<>();
		AtomicBoolean isCompleted = new AtomicBoolean(false); // Biến đánh dấu xem xử lý đã hoàn tất hay chưa

		FindPublisher<Zips> publisher = collection.find(and(gte("pop", minPopulation), lte("pop", maxPopulation)))
				.sort(descending("pop")).limit(1);
		Subscriber<Zips> subscriber = new Subscriber<Zips>() {
			private Subscription s;

			@Override
			public void onSubscribe(Subscription s) {
				this.s = s;
				s.request(1);
			}

			@Override
			public void onNext(Zips t) {
				result.add(t);
				this.s.cancel();
				isCompleted.set(true); // Đánh dấu xử lý đã hoàn tất khi nhận được kết quả
			}

			@Override
			public void onError(Throwable t) {
				t.printStackTrace();
				isCompleted.set(true); // Đánh dấu xử lý đã hoàn tất nếu gặp lỗi
			}

			@Override
			public void onComplete() {
				// Không cần làm gì ở đây vì chúng ta đã xử lý kết quả trong onNext()
			}
		};
		publisher.subscribe(subscriber);

		// Chờ cho đến khi xử lý hoàn tất
		while (!isCompleted.get()) {
			try {
				Thread.sleep(100); // Chờ 0.1 giây trước khi kiểm tra lại
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

}
