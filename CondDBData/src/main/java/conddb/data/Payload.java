package conddb.data;

// Generated Aug 25, 2014 4:52:00 PM by Hibernate Tools 3.4.0.CR1

//import conddb.data.deserialiser.*;
//import conddb.mappers.deserializers.PayloadDeserializer;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import conddb.data.exceptions.PayloadEncodingException;
import conddb.utils.hash.HashGenerator;

/**
 * Payload generated by hbm2java
 */
@Entity
@Table(name = "PAYLOAD")
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="hash")
//@JsonDeserialize(using=PayloadDeserializer.class)
//@JsonSerialize(using = PayloadSerializer.class)
public class Payload implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6319755970273574624L;
	private String hash;
	private String version;
	private String objectType;
	private byte[]  data;
	private Integer datasize; // size in bytes of the payload
	private String streamerInfo;
	private Timestamp insertionTime;
	private Set<Iov> iovs = new HashSet<Iov>(0);

	public Payload() {
		//default constructor creates an empty payload with only 
		// a String saying EMPTY
		this.objectType="DEFAULT";
		this.data = "EMPTY".getBytes();
		this.datasize = this.data.length;
		this.streamerInfo = "String";
		this.version = "0.0";
		try {
			this.hash = HashGenerator.md5Spring("EMPTY");
		} catch (PayloadEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Payload(String hash, String objectType, byte[] data,
			String streamerInfo, Timestamp insertionTime, String version) {
		this.hash = hash;
		this.objectType = objectType;
		this.data = data;
		this.streamerInfo = streamerInfo;
		this.insertionTime = insertionTime;
		this.version = version;
	}

	public Payload(String hash, String objectType, byte[] data,
			String streamerInfo, Timestamp insertionTime,  String version, Set<Iov> iovs) {
		this.hash = hash;
		this.objectType = objectType;
		this.data = data;
		this.streamerInfo = streamerInfo;
		this.insertionTime = insertionTime;
		this.version = version;
		this.iovs = iovs;
	}

	@Id
	@Column(name = "HASH", unique = true, nullable = false, length = 40)
	public String getHash() {
		return this.hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	@Column(name = "VERSION", nullable = false, length = 20)
	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Column(name = "OBJECT_TYPE", nullable = false, length = 100)
	public String getObjectType() {
		return this.objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	@Column(name = "DATA", nullable = false)
	@Lob
	public byte[] getData() {
		return this.data;
	}

	public void setData(byte[]  data) {
		this.data = data;
	}

	@Column(name = "STREAMER_INFO", nullable = true)
	@Lob
	public String getStreamerInfo() {
		return this.streamerInfo;
	}

	public void setStreamerInfo(String streamerInfo) {
		this.streamerInfo = streamerInfo;
	}
	
	@Column(name = "DATA_SIZE", nullable = false, precision = 12, scale = 0)
	public Integer getDatasize() {
		return datasize;
	}

	public void setDatasize(Integer datasize) {
		this.datasize = datasize;
	}

	//	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "INSERTION_TIME", nullable = false)
	public Timestamp getInsertionTime() {
		return this.insertionTime;
	}

	public void setInsertionTime(Timestamp insertionTime) {
		this.insertionTime = insertionTime;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "payload")
	public Set<Iov> getIovs() {
		return this.iovs;
	}

	public void setIovs(Set<Iov> iovs) {
		this.iovs = iovs;
	}

	@PrePersist
    public void prePersist() {
        Timestamp now = new Timestamp(new Date().getTime());
        this.insertionTime = now;
    }

}
