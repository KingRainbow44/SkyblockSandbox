package tk.skyblocksandbox.skyblocksandbox.entity.fishing;

import org.bukkit.entity.EntityType;
import tk.skyblocksandbox.skyblocksandbox.entity.SkyblockEntity;
import tk.skyblocksandbox.skyblocksandbox.entity.SkyblockEntityData;

public final class Yeti extends SkyblockEntity {

    public Yeti() {
        super(EntityType.PLAYER);
    }

    @Override
    public SkyblockEntityData getEntityData() {
        SkyblockEntityData entityData = new SkyblockEntityData();

        entityData.entityName = "Yeti";
        entityData.damage = 100;
        entityData.health = 2000000;
        entityData.level = 175;

        entityData.skinName = "Serializable";
        entityData.skinSignature = "B6YLY/krUu8EI67UBzVoOid/8VERTuDl9Ctc/ucpa97/eHGDjUWAi3+xAYd2hsZVAAFrmWB+/PZzA2ZNNDa78NVWfKfSboaoSjgNwmYmqJpOVCWjt7XA4corOeVdMJWccQkr5Z3HmQFBzLltOzc/FQIxqCaEKGAfLkT5ttykKH04s1WoiwhHyfie8kNnc+pEsFe1yfAi9FR7m2UmQ8eJDw6aLSNX" +
                "fwzWPkzzoSePMwUeKnfvGFacx14+WauCEMVLekUvw+oiXZTpIOdteIOeeuWbeFIv/A30xcUlO+dTeed7EJyBcu64LLbhJw9qQqIoB3cU2ue3w9M1dkRuQuXoI57ByiBBok3+F60Dz95RbvXZ1Dsl0WfwdHE44uxUfhG+Py/Seg66VB0eELcW/rDic4b7f2h1DWI1bYIHWfzlX3J0kA7cCsvO2LZBIENvA3kcPemQkD+BhCw" +
                "Q0h3xwnprwVjodOLsQfUtgzfEvoidI2lLjbVhErA2b3QzDM3+O6Cyl9LL0Z455qX5O9ygxBfp0qLj6h4MMpxAnggaNBlZs1Yw00GMU7Ltgffo46fZSYwuypqbq0DK73yJLw5OyhOqkVpSWzm2MM+1O54t6QMoGTzDE0a8On/0lZNmILGyOb39n+doeNXIGdFVTJ1sbYL4Bl34kW+iCuiq2uLNu62cvoc=";
        entityData.skinData = "ewogICJ0aW1lc3RhbXAiIDogMTYyMTE5NjA3NzQzNCwKICAicHJvZmlsZUlkIiA6ICIxOTI1MjFiNGVmZGI0MjVjODkzMWYwMmE4NDk2ZTExYiIsCiAgInByb2ZpbGVOYW1lIiA6ICJTZXJpYWxpemFibGUiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgI" +
                "CAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGRkMmQzYzZkMDFjMjc2MjI2YzdiMGQzNzcxMjJlMWE2NDdiMmZmYjViOWI1NGZhOThlYWMzN2JiMWQwOWQzYSIKICAgIH0KICB9Cn0=";

        return entityData;
    }
}
