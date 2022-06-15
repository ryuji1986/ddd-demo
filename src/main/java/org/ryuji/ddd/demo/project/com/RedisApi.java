//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.ryuji.ddd.demo.project.com;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

@Component
public class RedisApi {

    @Autowired
    private JedisPool jedisPool;

    public RedisApi() {
    }

    public Long expire(String key, int time) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.expire(key, time);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public void del(String... key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            if (key != null && key.length > 0) {
                if (key.length == 1) {
                    jedis.del(key[0]);
                } else {
                    jedis.del(key);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public String get(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return key == null ? null : jedis.get(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public String set(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.set(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public String set(String key, String value, int time) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.setex(key, time, value);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public Long incr(String key, Long delta) {
        Assert.isTrue(delta > 0L, "递增因子必须大于0");
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.incr(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public Boolean exists(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.exists(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public String hget(String key, String item) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.hget(key, item);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public Map<String, String> hmget(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.hgetAll(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public String hmset(String key, Map<String, String> map) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.hmset(key, map);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public Long hset(String key, String item, String value, int time) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Long res = jedis.hset(key, item, value);
            if (time > 0L) {
                this.expire(key, time);
            }
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public void hdel(String key, String... item) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.hdel(key, item);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public Long hincr(String key, String item, Long by) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.hincrBy(key, item, by);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public Boolean hexists(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.hexists(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public Set<String> hkeys(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.hkeys(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public Set<String> sget(String... key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.sunion(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public Long srem(String key, String... irem) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.srem(key, irem);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public Set<String> sdiff(String... key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.sdiff(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public Long sdiffstore(String dstkey, String... keys) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.sdiffstore(dstkey, keys);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public Boolean sismember(String key, String member) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.sismember(key, member);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public Long smove(String srckey, String dstkey, String member) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.smove(srckey, dstkey, member);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public Set<String> zrange(String key, String max, String min) {

        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.zrevrangeByLex(key, max, min);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public Long zrm(String key, String... vaules) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.zrem(key, vaules);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public Long zadd(String key, String value, Double score) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.zadd(key, score, value);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public Long zcard(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.zcard(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public String lindex(String key, Long index) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.lindex(key, index);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public String lset(String key, String value, Long time) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.lset(key, time, value);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public Long llen(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.llen(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public Long lrem(String key, Long count, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.lrem(key, count, value);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }


    /**
     * List头部追加记录
     * 将一个或多个值 value 插入到列表 key 的表头
     * 如果list不存在，则创建list 并进行push 操作
     *
     * @param key
     * @param value
     * @return
     */
    public Long lPush(String key, String... value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            long resultStatus = jedis.lpush(key, value);
            return resultStatus;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * List尾部追加记录
     * 将一个或多个值 value 插入到列表 key 的表尾(最右边)
     * 如果list不存在，一个空列表会被创建并执行 RPUSH 操作
     *
     * @param key
     * @param value
     * @return
     */
    public Long rPush(String key, String... value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            long resultStatus = jedis.rpush(key, value);
            return resultStatus;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public String lPop(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.lpop(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public String rPop(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.rpop(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }


    /**
     * 占用锁
     *
     * @param lockKey 锁key
     * @param lockId 锁id，必须拥有此id才能释放锁
     * @return 锁id
     */
    public String tryLock(String lockKey, String lockId) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            //占用锁同时设置失效时间
            String isSuccees = jedis.set(lockKey, lockId, "NX", "PX", 15);
            //占用锁成功返回锁id，否则返回null
            if ("OK".equals(isSuccees)) {
                return lockId;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * 释放锁
     *
     * @param lockKey 锁key
     * @param lockId  加锁id
     */
    public void unLock(String lockKey, String lockId) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            if (lockId != null) {
                //执行Lua代码删除lockId匹配的锁
                String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
                jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(lockId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

}
