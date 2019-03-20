package com.moebius.backend.domain.members;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface MemberRepository extends ReactiveMongoRepository<Member, ObjectId> {
	Mono<Member> findByName(String name);

	Mono<Member> findByEmail(String email);
}
